package com.shashavs.data

object TestJson {
    val response = """
        {
          "products": [
            {
              "id": 1,
              "title": "Product",
              "description": "Desc",
              "category": "Cat",
              "price": 10.0,
              "discountPercentage": 1.0,
              "rating": 4.0,
              "stock": 10,
              "tags": [],
              "brand": "Brand",
              "sku": "SKU",
              "weight": 1,
              "dimensions": {
                "width": 10.0,
                "height": 20.0,
                "depth": 30.0
              },
              "warrantyInformation": "1y",
              "shippingInformation": "Ship",
              "availabilityStatus": "In Stock",
              "reviews": [
                {
                  "rating": 5,
                  "comment": "Great",
                  "date": "2023-01-01",
                  "reviewerName": "John",
                  "reviewerEmail": "john@example.com"
                }
              ],
              "returnPolicy": "30d",
              "minimumOrderQuantity": 1,
              "meta": {
                "createdAt": "2023-01-01",
                "updatedAt": "2023-01-02",
                "barcode": "123",
                "qrCode": "qr"
              },
              "images": [],
              "thumbnail": "thumb"
            }
          ],
          "total": 100,
          "skip": 0,
          "limit": 30
        }
    """.trimIndent()
}
