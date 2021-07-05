package com.doubleslash.fifth.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.doubleslash.fifth.dto.BeerDTO;
import com.doubleslash.fifth.dto.LiquorDTO;
import com.doubleslash.fifth.dto.SimilarAlcoholDTO;
import com.doubleslash.fifth.dto.WineDTO;
import com.doubleslash.fifth.dto.response.AlcoholResponse;
import com.doubleslash.fifth.dto.response.LoveResponse;
import com.doubleslash.fifth.entity.User;
import com.doubleslash.fifth.entity.alcohol.Alcohol;
import com.doubleslash.fifth.entity.alcohol.AlcoholLove;
import com.doubleslash.fifth.entity.alcohol.Beer;
import com.doubleslash.fifth.entity.alcohol.Liquor;
import com.doubleslash.fifth.entity.alcohol.Wine;
import com.doubleslash.fifth.entity.review.Review;
import com.doubleslash.fifth.repository.AlcoholLoveRepository;
import com.doubleslash.fifth.repository.AlcoholRepository;
import com.doubleslash.fifth.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AlcoholService {

	private final AlcoholRepository alcoholRepository;
	private final UserRepository userRepository;
	private final AlcoholLoveRepository alcoholLoveRepository;

	public AlcoholResponse<?> findAlcohol(Long userId, Long alcoholId) {

        User user = userRepository.findById(userId).get();
        Alcohol alcohol = alcoholRepository.findById(alcoholId).get();
        String category = alcohol.getCategory();

        if (category.equals("양주")) {
            Liquor liquor = (Liquor) alcohol;
            return new AlcoholResponse<LiquorDTO>(new LiquorDTO(liquor, user));
        } else if (category.equals("세계맥주")) {
            Beer beer = (Beer) alcohol;
            return new AlcoholResponse<BeerDTO>(new BeerDTO(beer, user));
        } else if (category.equals("와인")) {
            Wine wine = (Wine) alcohol;
            return new AlcoholResponse<WineDTO>(new WineDTO(wine, user));
        }
        return null;
    }

    public AlcoholResponse<?> findAlcoholForGuest(Long alcoholId) {

        Alcohol alcohol = alcoholRepository.findById(alcoholId).get();
        String category = alcohol.getCategory();

        if (category.equals("양주")) {
            Liquor liquor = (Liquor) alcohol;
            return new AlcoholResponse<LiquorDTO>(new LiquorDTO(liquor));
        } else if (category.equals("세계맥주")) {
            Beer beer = (Beer) alcohol;
            return new AlcoholResponse<BeerDTO>(new BeerDTO(beer));
        } else if (category.equals("와인")) {
            Wine wine = (Wine) alcohol;
            return new AlcoholResponse<WineDTO>(new WineDTO(wine));
        }

        return null;
    }

	@Transactional
	public LoveResponse addLove(Long id, Long aid) throws IOException {
		
		User user = userRepository.findById(id).get();
		Alcohol alcohol = alcoholRepository.findById(aid).get();
		
		if (notAlreadyLove(user, alcohol) == null) {
			AlcoholLove alcoholLove = new AlcoholLove();
            alcoholLove.addLoveAlcohol(user, alcohol);
			alcoholLoveRepository.save(alcoholLove);
		}
		
		return new LoveResponse(true, alcohol.getAlcoholLoves().size());
	}
	
	@Transactional
	public LoveResponse cancelLove(Long id, Long aid) {
		
		User user = userRepository.findById(id).get();
		Alcohol alcohol = alcoholRepository.findById(aid).get();
		
		AlcoholLove alcoholLove = notAlreadyLove(user, alcohol);

        if (alcoholLove != null){
        	alcohol.getAlcoholLoves().remove(alcoholLove);
            alcoholLoveRepository.delete(alcoholLove);
        }
        return new LoveResponse(false, alcohol.getAlcoholLoves().size());
	}
	
    private AlcoholLove notAlreadyLove(User user, Alcohol alcohol) {
        Optional<AlcoholLove> alcoholLove = alcoholLoveRepository.findByUserAndAlcohol(user, alcohol);
        if (alcoholLove.isPresent()) {
        	return alcoholLove.get();
        } else {
        	return null;
        }
    } 
	
	// 별점 평균
    public static float starAvgByReview(List<Review> reviews) {
        float sum = 0;
        for (Review review : reviews) {
            sum += review.getStar();
        }
        return (float) (Math.round(sum / reviews.size() * 10) / 10.0);
    }
    
	// 주종별 사용자 주량
    public static String getUserDrinkStr(String nickname, float userDrink, float alcoholAbv, int alcoholMl) {

        float sojuAbv = 20.0f;
        float sojuAmount = (sojuAbv * userDrink * 360 / 100);
        float alcoholAmount = (alcoholAbv * alcoholMl / 100);
        float alcoholDrink = (float) (Math.round((sojuAmount / alcoholAmount) * 10) / 10.0);

        return "이 술에 맞는 " + nickname + " 님의 주량은 " + alcoholDrink + " 병입니다.";
    }

	// 유사 주류 데이터
	public List<Map<String, Object>> getSimilar(Long aid){
		List<SimilarAlcoholDTO> similarDto =  alcoholRepository.findSimilar(aid);
		
		List<Map<String, Object>> listResult = new ArrayList<Map<String,Object>>();
		for(int i=0; i<similarDto.size(); i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("aId", similarDto.get(i).getAid());
			map.put("aImage", similarDto.get(i).getImage());
			map.put("aName", similarDto.get(i).getName());
			listResult.add(map);
		}
		return listResult;
	}
	
}
