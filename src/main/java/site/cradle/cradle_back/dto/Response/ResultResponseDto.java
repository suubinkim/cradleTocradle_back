package site.cradle.cradle_back.dto.Response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultResponseDto {
    private String result;
    private String msg;

    public ResultResponseDto(String result, String msg) {
        this.result = result;
        this.msg = msg;
    }
}
