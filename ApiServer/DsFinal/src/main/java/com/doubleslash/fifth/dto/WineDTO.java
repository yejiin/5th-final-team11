package com.doubleslash.fifth.dto;

import static com.doubleslash.fifth.service.AlcoholService.getUserDrinkStr;
import static com.doubleslash.fifth.service.AlcoholService.starAvgByReview;

import java.util.Set;

import com.doubleslash.fifth.entity.User;
import com.doubleslash.fifth.entity.alcohol.AlcoholLove;
import com.doubleslash.fifth.entity.alcohol.Wine;

import lombok.Data;

@Data
public class WineDTO extends AlcoholDTO {

    private String country;
    
    private String area;
    
    private int flavor;
    
    private int body;
    
    private String source;

    public WineDTO(Wine wine, User user) {
        this.aid = wine.getId();
        this.name = wine.getName();
        this.category = wine.getCategory();
        this.image = wine.getImage();
        this.lowestPrice = wine.getLowestPrice();
        this.highestPrice = wine.getHighestPrice();
        this.ml = wine.getMl();
        this.abv = wine.getAbv();
        this.description = wine.getDescription();
        this.kind = wine.getKind();
        this.starAvg = starAvgByReview(wine.getReviews());
        this.starCnt = wine.getReviews().size();
        this.userDrink = getUserDrinkStr(user.getNickname(), user.getDrink(), wine.getAbv(), wine.getMl());
        this.country = wine.getCountry();
        this.area = wine.getArea();
        this.flavor = wine.getFlavor();
        this.body = wine.getBody();

        Set<AlcoholLove> alcoholLoves = wine.getAlcoholLoves();
        this.loveClick = alcoholLoves.stream()
                .filter(al -> al.getUser() == user)
                .findAny()
                .isPresent();
        
        this.source = "와인21";
    }

    public WineDTO(Wine wine) {
        this.aid = wine.getId();
        this.name = wine.getName();
        this.category = wine.getCategory();
        this.image = wine.getImage();
        this.lowestPrice = wine.getLowestPrice();
        this.highestPrice = wine.getHighestPrice();
        this.ml = wine.getMl();
        this.abv = wine.getAbv();
        this.description = wine.getDescription();
        this.kind = wine.getKind();
        this.starAvg = starAvgByReview(wine.getReviews());
        this.starCnt = wine.getReviews().size();
        this.country = wine.getCountry();
        this.area = wine.getArea();
        this.flavor = wine.getFlavor();
        this.body = wine.getBody();
        
        this.source = "와인21";
    }
}