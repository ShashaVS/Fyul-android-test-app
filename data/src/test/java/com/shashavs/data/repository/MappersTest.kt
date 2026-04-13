package com.shashavs.data.repository

import com.shashavs.data.TestDto
import org.junit.Assert.assertEquals
import org.junit.Test

class MappersTest {

    @Test
    fun `DimensionsDto toDomain`() {
        val dto = TestDto.dimensionsDto.copy()
        val expected = TestDto.dimensions.copy()
        assertEquals(expected, dto.toDomain())
    }

    @Test
    fun `ReviewDto toDomain`() {
        val dto = TestDto.reviewDto.copy()
        val expected = TestDto.review.copy()
        assertEquals(expected, dto.toDomain())
    }

    @Test
    fun `MetaDto toDomain`() {
        val dto = TestDto.metaDto.copy()
        val expected = TestDto.meta.copy()
        assertEquals(expected, dto.toDomain())
    }

    @Test
    fun `ProductDto toDomain`() {
        val dto = TestDto.productDto.copy()
        val expected = TestDto.product.copy()
        assertEquals(expected, dto.toDomain())
    }

    @Test
    fun `ProductsResponseDto toDomain`() {
        val dto = TestDto.productsResponseDto.copy()
        val expected = TestDto.productsPage.copy()
        assertEquals(expected, dto.toDomain())
    }
}
