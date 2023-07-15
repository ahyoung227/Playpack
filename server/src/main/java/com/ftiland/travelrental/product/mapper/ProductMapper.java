package com.ftiland.travelrental.product.mapper;

import com.ftiland.travelrental.common.utils.pagination.PageResponseDto;
import com.ftiland.travelrental.product.dto.FeaturedProductsResponseDto;
import com.ftiland.travelrental.product.dto.ProductDto;
import com.ftiland.travelrental.product.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class ProductMapper {
    public static FeaturedProductsResponseDto createFeaturedProductsResponseDto(List<Product> top3ByViewCount, List<Product> top3ByTotalRateScoreRatio, List<Product> top3ByBaseFeeZero) {
        FeaturedProductsResponseDto responseDTO = new FeaturedProductsResponseDto();
        responseDTO.setTop3ByViewCount(mapToProductDtoList(top3ByViewCount));
        responseDTO.setTop3ByTotalRateScoreRatio(mapToProductDtoList(top3ByTotalRateScoreRatio));
        responseDTO.setTop3ByBaseFeeZero(mapToProductDtoList(top3ByBaseFeeZero));
        return responseDTO;
    }

    public static PageResponseDto createPageResponseDto(Page<Product> productPage) {
        List<ProductDto> productDtoList = mapToProductDtoList(productPage.getContent());
        log.info("productDtoList { }", productDtoList);
        return PageResponseDto.of(productDtoList, productPage);
    }

    public static List<ProductDto> mapToProductDtoList(List<Product> products) {
        return products.stream()
                .map(ProductDto::from)
                .collect(Collectors.toList());
    }
}
