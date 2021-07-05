package com.doubleslash.fifth.dto;

import static com.doubleslash.fifth.service.AlcoholService.getUserDrinkStr;
import static com.doubleslash.fifth.service.AlcoholService.starAvgByReview;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.doubleslash.fifth.entity.User;
import com.doubleslash.fifth.entity.alcohol.AlcoholLove;
import com.doubleslash.fifth.entity.alcohol.Beer;

public class BeerDTO extends AlcoholDTO {
	
    private String subKind;
    
    private List<String> flavors;

    public BeerDTO(Beer beer, User user) {
        this.aid = beer.getId();
        this.name = beer.getName();
        this.category = beer.getCategory();
        this.image = beer.getImage();
        this.lowestPrice = beer.getLowestPrice();
        this.highestPrice = beer.getHighestPrice();
        this.ml = beer.getMl();
        this.abv = beer.getAbv();
        this.description = beer.getDescription();
        this.kind = beer.getKind();
        this.starAvg = starAvgByReview(beer.getReviews());
        this.starCnt = beer.getReviews().size();
        this.userDrink = getUserDrinkStr(user.getNickname(), user.getDrink(), beer.getAbv(), beer.getMl());
        this.subKind = beer.getSubKind();

        Set<AlcoholLove> alcoholLoves = beer.getAlcoholLoves();
        this.loveClick = alcoholLoves.stream()
                .filter(al -> al.getUser() == user)
                .findAny()
                .isPresent();

        flavors = new ArrayList<>();
        String flavortemp[] = beer.getFlavor().split("#");
        for (String temp : flavortemp) {
            flavors.add(temp);
        }
    }

    public BeerDTO(Beer beer) {
        this.aid = beer.getId();
        this.name = beer.getName();
        this.category = beer.getCategory();
        this.image = beer.getImage();
        this.lowestPrice = beer.getLowestPrice();
        this.highestPrice = beer.getHighestPrice();
        this.ml = beer.getMl();
        this.abv = beer.getAbv();
        this.description = beer.getDescription();
        this.kind = beer.getKind();
        this.starAvg = starAvgByReview(beer.getReviews());
        this.starCnt = beer.getReviews().size();
        this.subKind = beer.getSubKind();

        flavors = new ArrayList<>();
        String flavortemp[] = beer.getFlavor().split("#");
        for (String temp : flavortemp) {
            flavors.add(temp);
        }
    }
}