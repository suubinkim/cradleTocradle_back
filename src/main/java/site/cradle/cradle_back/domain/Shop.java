package site.cradle.cradle_back.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter // get 함수를 일괄적으로 만들어줍니다.
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity // DB 테이블 역할을 합니다.
public class Shop {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column
    private String link;

    @Column
    private String address;

    @Column
    private String description;

    @Column
    private String imgLink;

    @Column(nullable = false)
    private LocationType locationType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    //찜한 가게 생성
    public Shop(String title, String link, String address, User user, LocationType locationType, String description, String imgLink) {
        this.title = title;
        this.link = link;
        this.address = address;
        this.user = user;
        this.locationType = locationType;
        this.description = description;
        this.imgLink = imgLink;
    }

}
