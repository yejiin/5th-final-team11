package com.doubleslash.fifth.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.doubleslash.fifth.entity.User;
import com.doubleslash.fifth.entity.alcohol.AlcoholLove;
import com.doubleslash.fifth.entity.alcohol.Liquor;

import lombok.Data;

import static com.doubleslash.fifth.service.AlcoholService.getUserDrinkStr;
import static com.doubleslash.fifth.service.AlcoholService.starAvgByReview;

@Data
public class LiquorDTO extends AlcoholDTO {
	
	private List<String> flavors;
	
	private String source;
    
    public LiquorDTO(Liquor liquor, User user) {
        this.aid = liquor.getId();
        this.name = liquor.getName();
        this.category = liquor.getCategory();
        this.image = liquor.getImage();
        this.lowestPrice = liquor.getLowestPrice();
        this.highestPrice = liquor.getHighestPrice();
        this.ml = liquor.getMl();
        this.abv = liquor.getAbv();
        this.description = liquor.getDescription();
        this.kind = liquor.getKind();
        this.starAvg = starAvgByReview(liquor.getReviews());
        this.starCnt = liquor.getReviews().size();
        this.userDrink = getUserDrinkStr(user.getNickname(), user.getDrink(), liquor.getAbv(), liquor.getMl());

        Set<AlcoholLove> alcoholLoves = liquor.getAlcoholLoves();
        this.loveClick = alcoholLoves.stream()
                .filter(al -> al.getUser() == user)
                .findAny()
                .isPresent();
        
        this.flavors = new ArrayList<>();
        flavors.addAll(getFlavors(liquor.getFlavor().split("#")));
        
        String subFlavor = liquor.getSubFlavor();

        if (subFlavor != null) {
        	flavors.addAll(getFlavors(subFlavor.split("#")));
        }
    }

    public LiquorDTO(Liquor liquor) {
        this.aid = liquor.getId();
        this.name = liquor.getName();
        this.category = liquor.getCategory();
        this.image = liquor.getImage();
        this.lowestPrice = liquor.getLowestPrice();
        this.highestPrice = liquor.getHighestPrice();
        this.ml = liquor.getMl();
        this.abv = liquor.getAbv();
        this.description = liquor.getDescription();
        this.kind = liquor.getKind();
        this.starCnt = liquor.getReviews().size();
        this.starAvg = starAvgByReview(liquor.getReviews());
        
        this.flavors = new ArrayList<>();
        
        flavors.addAll(getFlavors(liquor.getFlavor().split("#")));
        
        String subFlavor = liquor.getSubFlavor();
        if (subFlavor != null) {
        	flavors.addAll(getFlavors(subFlavor.split("#")));
        } 
    }
    
    
    private ArrayList<String> getFlavors(String[] flavors) {
    	
    	ArrayList<String> res = new ArrayList<String>();
    	
    	for (String flavor : flavors) {
			res.add(flavor);
		}
    	
    	return res;
    }

}
