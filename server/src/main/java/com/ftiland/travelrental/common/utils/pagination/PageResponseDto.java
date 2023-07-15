package com.ftiland.travelrental.common.utils.pagination;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PageResponseDto {
    private Object data;
    private PageInfo pageInfo;

    private PageResponseDto(Object data, Page page) {
        this.data = data;
        this.pageInfo =
                new PageInfo(
                        page.getNumber(),
                        page.getSize(),
                        (int) page.getTotalElements(),
                        page.getTotalPages()
                );
    }

    public static PageResponseDto of(Object data, Page page) {
        return new PageResponseDto(data, page);
    }
}
