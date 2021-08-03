---
layout: default
title: Target Scraping
nav_order: 1
parent: Scraping
---

# Product scraping
## URL
A `GET` request is used to get items from the following Target product URL: 
`https://redsky.target.com/redsky_aggregations/v1/web/plp_search_v1`.


## Parameters
Certain parameters are required for returning the results of the website. The following parameters are what were
determined to be required or important for returning proper values

| Parameter name   | Parameter type | Default/permanent value                    | Notes                                                                                                        |
|:-----------------|:---------------|:-------------------------------------------|:-------------------------------------------------------------------------------------------------------------|
| key              | String         | "ff457966e64d5e877fdbad070f276d18ecec4a01" | This key is one that never changed when requesting items. It's possible that it does change at some point    |
| channel          | String         | "WEB"                                      | Default "WEB" value                                                                                          |
| count            | String/int     |                                            | # of items the Target URL should return to the application                                                   |
| keyword          | String         |                                            | The product to search for                                                                                    |
| page             | String         |                                            | Usually refers to: `s/{product}`                                                                             |
| pricing_store_id | int            |                                            | Store to search for items at                                                                                 |
| visitor_id       | String         |                                            | Uses a hexadecimal UUID generated via: `uuid.uuid1().hex.upper()`                                            |


## Returned Values
After values are returned from the target endpoint, the raw JSON is sent to Kafka for the Beam pipeline to consume and
parse.

A small example returned value is listed below. The return value is actually about 3x this size, but many values from
arrays has been removed to try to reduce the size of the data in this example

```
"result": {
      "data": {
        "search": {
          "search_recommendations": {
            "related_categories": [],
            "related_queries": []
          },
          "search_response": {
            "facet_list": [
              {
                "name": "d_categorytaxonomy",
                "type": "url",
                "display_name": "Category",
                "expand": true,
                "details": [
                  {
                    "display_name": "Women",
                    "url": "keyword=oranges&sort_by=relevance&count=10&offset=0&category=5xtd3",
                    "value": "5xtd3"
                  },
                  {
                    "display_name": "Kids",
                    "url": "keyword=oranges&sort_by=relevance&count=10&offset=0&category=xcoz4",
                    "value": "xcoz4"
                  },
                  {
                    "display_name": "Grocery",
                    "url": "keyword=oranges&sort_by=relevance&count=10&offset=0&category=5xt1a",
                    "value": "5xt1a"
                  },
                  {
                    "display_name": "Home",
                    "url": "keyword=oranges&sort_by=relevance&count=10&offset=0&category=5xtvd",
                    "value": "5xtvd"
                  },
                  {
                    "display_name": "Men",
                    "url": "keyword=oranges&sort_by=relevance&count=10&offset=0&category=18y1l",
                    "value": "18y1l"
                  },
                  {
                    "display_name": "Sports & Outdoors",
                    "url": "keyword=oranges&sort_by=relevance&count=10&offset=0&category=5xt85",
                    "value": "5xt85"
                  },
                  {
                    "display_name": "Patio & Garden",
                    "url": "keyword=oranges&sort_by=relevance&count=10&offset=0&category=5xtq9",
                    "value": "5xtq9"
                  },
                  {
                    "display_name": "Holiday Shop",
                    "url": "keyword=oranges&sort_by=relevance&count=10&offset=0&category=4ydi5",
                    "value": "4ydi5"
                  },
                  {
                    "display_name": "Health",
                    "url": "keyword=oranges&sort_by=relevance&count=10&offset=0&category=5xu1n",
                    "value": "5xu1n"
                  }
                ]
              },
              {
                "name": "d_pricerange",
                "type": "price",
                "display_name": "Price",
                "expand": true,
                "details": [
                  {
                    "display_name": "$0 &nbsp;&ndash;&nbsp; $15",
                    "facet_id": "5zja2",
                    "url": "d_pricerange:0000 $0 - $15",
                    "value": "0000 $0 - $15"
                  },
                  {
                    "display_name": "$15 &nbsp;&ndash;&nbsp; $25",
                    "facet_id": "5zja3",
                    "url": "d_pricerange:0001 $15 - $25",
                    "value": "0001 $15 - $25"
                  },
                  {
                    "display_name": "$25 &nbsp;&ndash;&nbsp; $50",
                    "facet_id": "5zja4",
                    "url": "d_pricerange:0002 $25 - $50",
                    "value": "0002 $25 - $50"
                  },
                  {
                    "display_name": "$50 &nbsp;&ndash;&nbsp; $100",
                    "facet_id": "5zja5",
                    "url": "d_pricerange:0003 $50 - $100",
                    "value": "0003 $50 - $100"
                  },
                  {
                    "display_name": "$100 &nbsp;&ndash;&nbsp; $150",
                    "facet_id": "5zja6",
                    "url": "d_pricerange:0004 $100 - $150",
                    "value": "0004 $100 - $150"
                  },
                  {
                    "display_name": "$150 &nbsp;&ndash;&nbsp; $200",
                    "facet_id": "5zja7",
                    "url": "d_pricerange:0005 $150 - $200",
                    "value": "0005 $150 - $200"
                  },
                  {
                    "display_name": "$200 &nbsp;&ndash;&nbsp; $300",
                    "facet_id": "5zja8",
                    "url": "d_pricerange:0006 $200 - $300",
                    "value": "0006 $200 - $300"
                  }
                ]
              },
              {
                "name": "d_sellers_all",
                "type": "checkbox",
                "display_name": "Sold by",
                "expand": true,
                "details": [
                  {
                    "display_name": "Target",
                    "facet_id": "dq4mn",
                    "url": "d_sellers_all:0000 target",
                    "value": "0000 target"
                  },
                  {
                    "display_name": "  PSK COLLECTIVE",
                    "facet_id": "sqhja",
                    "url": "d_sellers_all:0001   psk collective",
                    "value": "0001   psk collective"
                  },
                  {
                    "display_name": "Alexia Admor",
                    "facet_id": "hmh37",
                    "url": "d_sellers_all:0016 alexia admor",
                    "value": "0016 alexia admor"
                  },
                  {
                    "display_name": "White Mark Universal",
                    "facet_id": "iqqcy",
                    "url": "d_sellers_all:0301 white mark universal",
                    "value": "0301 white mark universal"
                  }
                ]
              },
              {
                "name": "d_channel",
                "type": "checkbox",
                "display_name": "Shipping & Pickup",
                "expand": false,
                "details": [
                  {
                    "display_name": "buy online & pick up",
                    "facet_id": "5zl7w",
                    "url": "d_channel:buy online & pick up",
                    "value": "buy online & pick up"
                  },
                  {
                    "display_name": "in stores",
                    "facet_id": "5zkty",
                    "url": "d_channel:in stores",
                    "value": "in stores"
                  },
                  {
                    "display_name": "shipping",
                    "facet_id": "5zktx",
                    "url": "d_channel:shipping",
                    "value": "shipping"
                  },
                  {
                    "display_name": "include out of stock",
                    "facet_id": "fwtfr",
                    "url": "d_channel:include out of stock",
                    "value": "include out of stock"
                  },
                  {
                    "display_name": "same day delivery",
                    "facet_id": "cl92v",
                    "url": "d_channel:same day delivery",
                    "value": "same day delivery"
                  }
                ]
              },
              {
                "name": "d_item_type_all",
                "type": "checkbox",
                "display_name": "Type",
                "expand": false,
                "details": [
                  {
                    "display_name": "A-line Dresses",
                    "facet_id": "pn2p7",
                    "url": "d_item_type_all:a-line dresses",
                    "value": "a-line dresses"
                  },
                  {
                    "display_name": "Zip-Up Sweatshirts",
                    "facet_id": "tu706",
                    "url": "d_item_type_all:zip-up sweatshirts",
                    "value": "zip-up sweatshirts"
                  }
                ]
              },
              {
                "name": "d_brand_all",
                "type": "checkbox",
                "display_name": "Brand",
                "expand": false,
                "details": [
                  {
                    "display_name": "5 Hour Energy",
                    "facet_id": "4vr5g",
                    "url": "d_brand_all:5 hour energy",
                    "value": "5 hour energy"
                  },
                  {
                    "display_name": "A New Day",
                    "facet_id": "xrye7",
                    "url": "d_brand_all:a new day",
                    "value": "a new day"
                  },
                  {
                    "display_name": "Adrenaline Shoc",
                    "facet_id": "q643letwf2w",
                    "url": "d_brand_all:adrenaline shoc",
                    "value": "adrenaline shoc"
                  },
                  {
                    "display_name": "ZOA",
                    "facet_id": "q643leflqtf",
                    "url": "d_brand_all:zoa",
                    "value": "zoa"
                  }
                ]
              },
              {
                "name": "d_package_quantity_fb",
                "type": "checkbox",
                "display_name": "Package Quantity",
                "expand": false,
                "details": [
                  {
                    "display_name": "4pk",
                    "facet_id": "cxusg",
                    "url": "d_package_quantity_fb:0000 4pk",
                    "value": "0000 4pk"
                  },
                  {
                    "display_name": "6pk",
                    "facet_id": "dzf32",
                    "url": "d_package_quantity_fb:0001 6pk",
                    "value": "0001 6pk"
                  },
                  {
                    "display_name": "8pk",
                    "facet_id": "y4gop",
                    "url": "d_package_quantity_fb:0002 8pk",
                    "value": "0002 8pk"
                  },
                  {
                    "display_name": "10pk",
                    "facet_id": "jotjq",
                    "url": "d_package_quantity_fb:0004 10pk",
                    "value": "0004 10pk"
                  },
                  {
                    "display_name": "12pk",
                    "facet_id": "73r96",
                    "url": "d_package_quantity_fb:0005 12pk",
                    "value": "0005 12pk"
                  },
                  {
                    "display_name": "18pk",
                    "facet_id": "oxeg8",
                    "url": "d_package_quantity_fb:0007 18pk",
                    "value": "0007 18pk"
                  }
                ]
              },
              {
                "name": "d_deals",
                "type": "checkbox",
                "display_name": "Deals",
                "expand": false,
                "details": [
                  {
                    "display_name": "All Deals",
                    "facet_id": "akkos",
                    "url": "d_deals:all deals",
                    "value": "all deals"
                  },
                  {
                    "display_name": "BOGO",
                    "facet_id": "55e69",
                    "url": "d_deals:bogo",
                    "value": "bogo"
                  },
                  {
                    "display_name": "Buy and Save",
                    "facet_id": "55e6t",
                    "url": "d_deals:buy and save",
                    "value": "buy and save"
                  },
                  {
                    "display_name": "Clearance",
                    "facet_id": "5tdv1",
                    "url": "d_deals:clearance",
                    "value": "clearance"
                  },
                  {
                    "display_name": "Sale",
                    "facet_id": "5tdv0",
                    "url": "d_deals:sale",
                    "value": "sale"
                  },
                  {
                    "display_name": "Weekly Ad",
                    "facet_id": "55dgn",
                    "url": "d_deals:weekly ad",
                    "value": "weekly ad"
                  }
                ]
              },
              {
                "name": "d_dietary_needs",
                "type": "checkbox",
                "display_name": "Dietary Needs",
                "expand": false,
                "details": [
                  {
                    "display_name": "Organic",
                    "facet_id": "s2ozl",
                    "url": "d_dietary_needs:0000 organic",
                    "value": "0000 organic"
                  },
                  {
                    "display_name": "Gluten-free",
                    "facet_id": "b8gba",
                    "url": "d_dietary_needs:0001 gluten-free",
                    "value": "0001 gluten-free"
                  },
                  {
                    "display_name": "Ketogenic",
                    "facet_id": "94abm",
                    "url": "d_dietary_needs:0002 ketogenic",
                    "value": "0002 ketogenic"
                  },
                  {
                    "display_name": "Non-GMO",
                    "facet_id": "sjhan",
                    "url": "d_dietary_needs:0038 non-gmo",
                    "value": "0038 non-gmo"
                  },
                  {
                    "display_name": "Plant-based",
                    "facet_id": "7vn3n",
                    "url": "d_dietary_needs:0041 plant-based",
                    "value": "0041 plant-based"
                  },
                  {
                    "display_name": "Sodium Free",
                    "facet_id": "p9ubl",
                    "url": "d_dietary_needs:0043 sodium free",
                    "value": "0043 sodium free"
                  },
                  {
                    "display_name": "Sugar Free",
                    "facet_id": "idmd0",
                    "url": "d_dietary_needs:0045 sugar free",
                    "value": "0045 sugar free"
                  }
                ]
              },
              {
                "name": "d_health_facts_all",
                "type": "checkbox",
                "display_name": "Health Facts",
                "expand": false,
                "details": [
                  {
                    "display_name": "Gluten-free",
                    "facet_id": "vsk4f",
                    "url": "d_health_facts_all:gluten-free",
                    "value": "gluten-free"
                  },
                  {
                    "display_name": "Vegan",
                    "facet_id": "2lftp",
                    "url": "d_health_facts_all:vegan",
                    "value": "vegan"
                  }
                ]
              },
              {
                "name": "d_flavor_all",
                "type": "checkbox",
                "display_name": "Flavor",
                "expand": false,
                "details": [
                  {
                    "display_name": "Vanilla",
                    "facet_id": "55k6l",
                    "url": "d_flavor_all:vanilla",
                    "value": "vanilla"
                  },
                  {
                    "display_name": "Watermelon",
                    "facet_id": "55k6c",
                    "url": "d_flavor_all:watermelon",
                    "value": "watermelon"
                  }
                ]
              },
              {
                "name": "d_pattern_all",
                "type": "checkbox",
                "display_name": "Pattern",
                "expand": false,
                "details": [
                  {
                    "display_name": "Rainbow",
                    "facet_id": "xu07h",
                    "url": "d_pattern_all:rainbow",
                    "value": "rainbow"
                  },
                  {
                    "display_name": "Waffle",
                    "facet_id": "t4qwm",
                    "url": "d_pattern_all:waffle",
                    "value": "waffle"
                  }
                ]
              },
              {
                "name": "d_certificiations_all",
                "type": "checkbox",
                "display_name": "Sustainability Claims and Certifications",
                "expand": false,
                "details": [
                  {
                    "display_name": "Fair Trade Certified",
                    "facet_id": "4ecsm",
                    "url": "d_certificiations_all:fair trade certified",
                    "value": "fair trade certified"
                  },
                  {
                    "display_name": "Non-GMO",
                    "facet_id": "fhxjh",
                    "url": "d_certificiations_all:non-gmo",
                    "value": "non-gmo"
                  },
                  {
                    "display_name": "Recycled Polyester",
                    "facet_id": "9hbmel7qt73",
                    "url": "d_certificiations_all:recycled polyester",
                    "value": "recycled polyester"
                  },
                  {
                    "display_name": "STANDARD 100 by OEKO-TEX",
                    "facet_id": "9hbmel5b6r8",
                    "url": "d_certificiations_all:standard 100 by oeko-tex",
                    "value": "standard 100 by oeko-tex"
                  }
                ]
              },
              {
                "name": "d_target_audience_all",
                "type": "checkbox",
                "display_name": "Age",
                "expand": false,
                "details": [
                  {
                    "display_name": "All Ages",
                    "facet_id": "8fvtm",
                    "url": "d_target_audience_all:0008 all ages",
                    "value": "0008 all ages"
                  }
                ]
              },
              {
                "name": "d_sleeve_length",
                "type": "checkbox",
                "display_name": "Sleeve Length",
                "expand": false,
                "details": [
                  {
                    "display_name": "Sleeveless",
                    "facet_id": "5xv9w",
                    "url": "d_sleeve_length:sleeveless",
                    "value": "sleeveless"
                  }
                ]
              }
            ],
            "visual_facet_list": [
              {
                "name": "d_deals",
                "type": "checkbox",
                "display_name": "Deals",
                "expand": false,
                "details": [
                  {
                    "display_name": "Weekly Ad",
                    "facet_canonical": "weekly-ad",
                    "facet_id": "55dgn",
                    "facet_source": "FACET_CLICKS",
                    "label_image_url": "https://target.scene7.com/is/image/Target/GUEST_ae03a453-caee-4274-b87a-9c37654c3c4c",
                    "url": "d_deals:weekly ad",
                    "value": "weekly ad"
                  }
                ]
              },
              {
                "name": "d_item_type_all",
                "type": "checkbox",
                "display_name": "Type",
                "expand": false,
                "details": [
                  {
                    "display_name": "Throw Pillows",
                    "facet_canonical": "throw-pillows",
                    "facet_id": "mbyui",
                    "facet_source": "FACET_CLICKS",
                    "label_image_url": "https://target.scene7.com/is/image/Target/GUEST_2874c337-8196-4b35-8317-015b59a3f967",
                    "url": "d_item_type_all:throw pillows",
                    "value": "throw pillows"
                  },
                  {
                    "display_name": "tank tops",
                    "facet_canonical": "tank-tops",
                    "facet_id": "t0hxy",
                    "facet_source": "FACET_CLICKS",
                    "label_image_url": "https://target.scene7.com/is/image/Target/GUEST_f3f152c6-e111-4b6e-85ef-06976703984a",
                    "url": "d_item_type_all:tank tops",
                    "value": "tank tops"
                  }
                ]
              },
              {
                "name": "d_brand_all",
                "type": "checkbox",
                "display_name": "Brand",
                "expand": false,
                "details": [
                  {
                    "display_name": "Simply Beverages",
                    "facet_canonical": "simply-beverages",
                    "facet_id": "ez23z",
                    "facet_source": "FACET_CLICKS",
                    "label_image_url": "https://target.scene7.com/is/image/Target/GUEST_c20d98e1-1d41-4173-9f90-bc0790e3ba09",
                    "url": "d_brand_all:simply beverages",
                    "value": "simply beverages"
                  }
                ]
              },
              {
                "name": "d_color_all",
                "type": "checkbox",
                "display_name": "Color",
                "expand": false,
                "details": [
                  {
                    "display_name": "Yellow",
                    "facet_canonical": "yellow",
                    "facet_id": "5y72c",
                    "facet_source": "FACET_CLICKS",
                    "label_image_url": "https://target.scene7.com/is/image/Target/yellowimage_upload-210318_1616101506307",
                    "url": "d_color_all:yellow",
                    "value": "yellow"
                  }
                ]
              },
              {
                "name": "d_product_color",
                "type": "image",
                "display_name": "Color",
                "expand": false,
                "details": [
                  {
                    "display_name": "Multicolored",
                    "facet_canonical": "multicolored",
                    "facet_id": "gup4zc5xkw9",
                    "facet_source": "FACET_CLICKS",
                    "label_image_url": "https://target.scene7.com/is/image/Target/GUEST_0568aa11-fa94-49fa-b18d-59c3edc92ded",
                    "url": "d_product_color:multicolored",
                    "value": "multicolored"
                  }
                ]
              }
            ],
            "typed_metadata": {
              "count": 10,
              "current_page": 1,
              "keyword": "oranges",
              "offset": 0,
              "sort_by": "relevance",
              "total_pages": 51,
              "total_results": 502
            },
            "sort_options": [
              {
                "name": "relevance",
                "value": "relevance"
              },
              {
                "name": "Featured",
                "value": "Featured"
              },
              {
                "name": "PriceLow",
                "value": "price-low to high"
              },
              {
                "name": "PriceHigh",
                "value": "price-high to low"
              },
              {
                "name": "RatingHigh",
                "value": "average ratings"
              },
              {
                "name": "bestselling",
                "value": "best seller"
              },
              {
                "name": "newest",
                "value": "newest"
              }
            ],
            "experiments_viewed": {
              "xv": [
                {
                  "xc": "1",
                  "xt": "32090df0-e911-4a5f-bb6e-363cc060f8c6"
                }
              ]
            }
          },
          "products": [
            {
              "__typename": "ProductSummary",
              "tcin": "15026732",
              "original_tcin": "15026732",
              "item": {
                "relationship_type": "Stand Alone",
                "relationship_type_code": "SA",
                "merchandise_classification": {
                  "class_id": 1,
                  "department_id": 267
                },
                "eligibility_rules": {
                  "add_on": {
                    "is_active": false
                  },
                  "grocery": {
                    "is_active": true
                  },
                  "scheduled_delivery": {
                    "is_active": true
                  }
                },
                "enrichment": {
                  "buy_url": "https://www.target.com/p/navel-orange-each/-/A-15026732",
                  "images": {
                    "primary_image_url": "https://target.scene7.com/is/image/Target/GUEST_b7943030-a555-4430-bdc5-832ec57dca95",
                    "alternate_image_urls": [
                      "https://target.scene7.com/is/image/Target/GUEST_e2d6ccc4-648e-46ae-b68e-f8bc9aa3b906",
                      "https://target.scene7.com/is/image/Target/GUEST_11e8bf97-a033-405f-9be0-80256d42d37f"
                    ]
                  }
                },
                "dpci": "267-01-4012",
                "cart_add_on_threshold": 35.0,
                "product_description": {
                  "title": "Navel Orange - Each",
                  "bullet_descriptions": [
                    "<B>Contains:</B> Does Not Contain Any of the 8 Major Allergens",
                    "<B>Form:</B> Whole",
                    "<B>State of Readiness:</B> Ready to Eat",
                    "<B>Package Quantity:</B> 1",
                    "<B>Pre-package preparation:</B> Raw",
                    "<B>Package type:</B> Individual Item Multi-Serving",
                    "<B>Net weight:</B> 1.0 Pounds",
                    "<B>Country of Origin:</B> Varies, Please See Label"
                  ],
                  "soft_bullets": {
                    "bullets": [
                      "Fresh snacking",
                      "High in fiber",
                      "Source of vitamin C",
                      "Low in cholesterol"
                    ]
                  }
                },
                "product_vendors": [
                  {
                    "vendor_name": "Pro Citrus Network, Inc.",
                    "id": "1980783"
                  },
                  {
                    "vendor_name": "Armstrong Produce, LTD",
                    "id": "1980532"
                  }
                ],
                "fulfillment": {}
              },
              "promotions": [],
              "price": {
                "current_retail": 0.95,
                "formatted_current_price": "$0.95",
                "formatted_current_price_type": "reg"
              },
              "ratings_and_reviews": {
                "statistics": {
                  "rating": {
                    "average": 3.65,
                    "count": 31,
                    "secondary_averages": [
                      {
                        "id": "Value",
                        "label": "value",
                        "value": 3.43
                      },
                      {
                        "id": "Taste",
                        "label": "taste",
                        "value": 3.33
                      },
                      {
                        "id": "Quality",
                        "label": "quality",
                        "value": 3.13
                      }
                    ]
                  }
                }
              }
            },
            {
              "__typename": "ProductSummary",
              "tcin": "51920491",
              "original_tcin": "51920491",
              "item": {
                "relationship_type": "Stand Alone",
                "relationship_type_code": "SA",
                "merchandise_classification": {
                  "class_id": 50,
                  "department_id": 266
                },
                "eligibility_rules": {
                  "add_on": {
                    "is_active": false
                  },
                  "grocery": {
                    "is_active": false
                  },
                  "scheduled_delivery": {
                    "is_active": true
                  }
                },
                "enrichment": {
                  "buy_url": "https://www.target.com/p/organic-navel-oranges-3lb-bag/-/A-51920491",
                  "images": {
                    "primary_image_url": "https://target.scene7.com/is/image/Target/GUEST_2dfa82a3-d583-469c-8f5c-2fa2feba7b07",
                    "alternate_image_urls": [
                      "https://target.scene7.com/is/image/Target/GUEST_471f61dc-ac24-4e8c-8752-886e02d5f32b"
                    ]
                  }
                },
                "dpci": "266-50-0008",
                "cart_add_on_threshold": 35.0,
                "product_description": {
                  "title": "Organic Navel Oranges - 3lb Bag",
                  "bullet_descriptions": [
                    "<B>Contains:</B> Does Not Contain Any of the 8 Major Allergens",
                    "<B>Dietary Needs:</B> Organic Certified",
                    "<B>Form:</B> Pieces",
                    "<B>State of Readiness:</B> Ready to Eat",
                    "<B>Package Quantity:</B> 1",
                    "<B>Package type:</B> Individual Item Multi-Serving",
                    "<B>Net weight:</B> 3 Pounds",
                    "<B>Country of Origin:</B> United States"
                  ]
                },
                "product_vendors": [
                  {
                    "vendor_name": "Armstrong Produce, LTD",
                    "id": "1980532"
                  }
                ],
                "fulfillment": {},
                "primary_brand": {
                  "canonical_url": "/b/bee-sweet-citrus/-/N-1ru7y",
                  "name": "Bee Sweet Citrus",
                  "facet_id": "1ru7y"
                }
              },
              "promotions": [],
              "price": {
                "current_retail": 5.39,
                "formatted_current_price": "$5.39",
                "formatted_current_price_type": "reg"
              },
              "ratings_and_reviews": {
                "statistics": {
                  "rating": {
                    "average": 3.31,
                    "count": 13,
                    "secondary_averages": [
                      {
                        "id": "Quality",
                        "label": "quality",
                        "value": 4.2
                      },
                      {
                        "id": "Taste",
                        "label": "taste",
                        "value": 4.4
                      },
                      {
                        "id": "Value",
                        "label": "value",
                        "value": 4.0
                      }
                    ]
                  }
                }
              }
            },
            {
              "__typename": "ProductSummary",
              "tcin": "53993954",
              "original_tcin": "53993954",
              "item": {
                "relationship_type": "Stand Alone",
                "relationship_type_code": "SA",
                "merchandise_classification": {
                  "class_id": 4,
                  "department_id": 284
                },
                "eligibility_rules": {
                  "add_on": {
                    "is_active": false
                  },
                  "grocery": {
                    "is_active": false
                  },
                  "scheduled_delivery": {
                    "is_active": true
                  }
                },
                "enrichment": {
                  "buy_url": "https://www.target.com/p/uncle-matt-s-organic-pulp-free-orange-juice-52-fl-oz/-/A-53993954",
                  "images": {
                    "primary_image_url": "https://target.scene7.com/is/image/Target/GUEST_694e02e6-77a3-4945-8513-b78d4d279ab4",
                    "alternate_image_urls": [
                      "https://target.scene7.com/is/image/Target/GUEST_e59164fe-9971-4be9-a86d-0350ee51fef5",
                      "https://target.scene7.com/is/image/Target/GUEST_04473283-9f00-43d1-8172-f89e191ab598",
                      "https://target.scene7.com/is/image/Target/GUEST_c16b2e17-e019-490f-9e64-e7266a0fadee",
                      "https://target.scene7.com/is/image/Target/GUEST_3091a01a-e6c4-41c0-80a6-90cb0f1ae58f",
                      "https://target.scene7.com/is/image/Target/GUEST_cf68cdc9-e8c4-4c72-a01d-1a2f59d52e52",
                      "https://target.scene7.com/is/image/Target/GUEST_2d51b347-7255-46b2-9e45-4760d6606a77"
                    ]
                  }
                },
                "dpci": "284-04-0380",
                "cart_add_on_threshold": 35.0,
                "product_description": {
                  "title": "Uncle Matt's Organic Pulp Free Orange Juice - 52 fl oz",
                  "bullet_descriptions": [
                    "<B>Contains:</B> Does Not Contain Any of the 8 Major Allergens",
                    "<B>Form:</B> Liquid",
                    "<B>State of Readiness:</B> Ready to Drink",
                    "<B>Package Quantity:</B> 1",
                    "<B>Package type:</B> Individual Item Multi-Serving",
                    "<B>Percentage juice content 1:</B> 100",
                    "<B>Pulp content 1:</B> No Pulp",
                    "<B>Net weight:</B> 52 fl oz (US)",
                    "<B>Beverage container material:</B> Plastic"
                  ],
                  "soft_bullets": {
                    "bullets": [
                      "\u2022 Uncle Matt's is a delicious blend of organic Hamlin and Valencia oranges",
                      "\u2022 No added sweeteners",
                      "\u2022 Certified organic, and we don't add any flavor packets or peel oil",
                      "\u2022 Certified glyphosate residue free",
                      "\u2022 We are committed to supporting family farmers and to helping farms convert to organic",
                      "\u2022 Ready to drink",
                      "\u2022 Keep Refrigerated\""
                    ]
                  }
                },
                "product_vendors": [
                  {
                    "vendor_name": "Dean Dairy Fluid NSBT",
                    "id": "1995391"
                  },
                  {
                    "vendor_name": "PET DAIRY",
                    "id": "1018279"
                  }
                ],
                "fulfillment": {},
                "primary_brand": {
                  "canonical_url": "/b/uncle-matt-s-organic/-/N-m5j9y",
                  "name": "Uncle Matt's Organic",
                  "facet_id": "m5j9y"
                }
              },
              "promotions": [],
              "price": {
                "current_retail": 6.69,
                "formatted_current_price": "$6.69",
                "formatted_current_price_type": "reg"
              },
              "ratings_and_reviews": {
                "statistics": {
                  "rating": {
                    "average": 4.2,
                    "count": 10,
                    "secondary_averages": [
                      {
                        "id": "Value",
                        "label": "value",
                        "value": 3.88
                      },
                      {
                        "id": "Taste",
                        "label": "taste",
                        "value": 4.0
                      },
                      {
                        "id": "Quality",
                        "label": "quality",
                        "value": 4.0
                      }
                    ]
                  }
                }
              }
            },
            {
              "__typename": "ProductSummary",
              "tcin": "14127772",
              "original_tcin": "14127772",
              "item": {
                "relationship_type": "Stand Alone",
                "relationship_type_code": "SA",
                "merchandise_classification": {
                  "class_id": 5,
                  "department_id": 203
                },
                "eligibility_rules": {
                  "add_on": {
                    "is_active": false
                  },
                  "scheduled_delivery": {
                    "is_active": true
                  }
                },
                "enrichment": {
                  "buy_url": "https://www.target.com/p/sanpellegrino-blood-orange-aranciata-rossa-italian-sparkling-drinks-6pk-11-15-fl-oz-cans/-/A-14127772",
                  "images": {
                    "primary_image_url": "https://target.scene7.com/is/image/Target/GUEST_c62fee42-ec92-4def-88af-5d9a52551dc8",
                    "alternate_image_urls": [
                      "https://target.scene7.com/is/image/Target/GUEST_ab0515cb-2622-4d43-800a-5061a6f37e22",
                      "https://target.scene7.com/is/image/Target/GUEST_04e9425b-a01d-4c66-beec-7ca0415774ee",
                      "https://target.scene7.com/is/image/Target/GUEST_36d0aba2-3060-42d6-92a6-62772349de66",
                      "https://target.scene7.com/is/image/Target/GUEST_17517541-f942-48dc-8e0f-3e6c84f5347b"
                    ]
                  },
                  "videos": [
                    {
                      "is_list_page_eligible": false,
                      "video_files": [
                        {
                          "mime_type": "video/mp4",
                          "video_url": "https://target.scene7.com/is/content/Target/GUEST_a9d7aeb9-efe9-4de1-b29a-0824c6c8a7fe_Flash9_Autox720p_2600k"
                        }
                      ]
                    }
                  ]
                },
                "dpci": "203-05-0652",
                "cart_add_on_threshold": 35.0,
                "product_description": {
                  "title": "Sanpellegrino Blood Orange/Aranciata Rossa Italian Sparkling Drinks - 6pk/11.15 fl oz Cans",
                  "bullet_descriptions": [
                    "<B>Contains:</B> Does Not Contain Any of the 8 Major Allergens",
                    "<B>Form:</B> Liquid",
                    "<B>State of Readiness:</B> Ready to Drink",
                    "<B>Package Quantity:</B> 6",
                    "<B>Package type:</B> Multi-Pack Single Servings",
                    "<B>Carbonation type 1:</B> Sparkling",
                    "<B>Net weight:</B> 66.9 fl oz (US)",
                    "<B>Beverage container material:</B> Metal"
                  ],
                  "soft_bullets": {
                    "bullets": [
                      "\u2022 Six 11.15 fluid ounce cans",
                      "\u2022 Naturally flavored Blood Orange/Aranciata Rossa Italian Sparkling Drinks",
                      "\u2022 Made with blood orange juice, real sugar, and sparkling water",
                      "\u2022 No artificial colors, flavors, sweeteners or preservatives",
                      "\u2022 Signature foil cover keeps each can clean"
                    ]
                  }
                },
                "product_vendors": [
                  {
                    "vendor_name": "NESTLE USA INC",
                    "id": "3553761"
                  },
                  {
                    "vendor_name": "NESTLE WATERS N AMERICA",
                    "id": "2431185"
                  },
                  {
                    "vendor_name": "BEVERAGE NETWORK",
                    "id": "1034606"
                  }
                ],
                "fulfillment": {},
                "primary_brand": {
                  "canonical_url": "/b/sanpellegrino/-/N-mk4b9",
                  "name": "Sanpellegrino",
                  "facet_id": "mk4b9"
                }
              },
              "promotions": [],
              "price": {
                "current_retail": 5.79,
                "formatted_current_price": "$5.79",
                "formatted_current_price_type": "reg"
              },
              "ratings_and_reviews": {
                "statistics": {
                  "rating": {
                    "average": 4.71,
                    "count": 28,
                    "secondary_averages": [
                      {
                        "id": "Value",
                        "label": "value",
                        "value": 4.33
                      },
                      {
                        "id": "Taste",
                        "label": "taste",
                        "value": 4.88
                      },
                      {
                        "id": "Quality",
                        "label": "quality",
                        "value": 4.75
                      }
                    ]
                  }
                }
              }
            },
            {
              "__typename": "ProductSummary",
              "tcin": "78821525",
              "original_tcin": "78821525",
              "item": {
                "relationship_type": "Stand Alone",
                "relationship_type_code": "SA",
                "merchandise_classification": {
                  "class_id": 70,
                  "department_id": 271
                },
                "eligibility_rules": {
                  "add_on": {
                    "is_active": false
                  },
                  "scheduled_delivery": {
                    "is_active": true
                  }
                },
                "enrichment": {
                  "buy_url": "https://www.target.com/p/aha-orange-grapefruit-sparkling-water-8pk-12-fl-oz-cans/-/A-78821525",
                  "images": {
                    "primary_image_url": "https://target.scene7.com/is/image/Target/GUEST_61efbf32-6e32-4718-8ae4-34528e1afe96",
                    "alternate_image_urls": [
                      "https://target.scene7.com/is/image/Target/GUEST_1cc59672-fb5e-4edc-b74c-69481a35ca24",
                      "https://target.scene7.com/is/image/Target/GUEST_e76f6ca3-d938-4a58-8d1f-0a903cef1d25"
                    ]
                  }
                },
                "dpci": "271-70-6863",
                "cart_add_on_threshold": 35.0,
                "product_description": {
                  "title": "AHA Orange + Grapefruit Sparkling Water - 8pk/12 fl oz Cans",
                  "bullet_descriptions": [
                    "<B>Contains:</B> Does Not Contain Any of the 8 Major Allergens",
                    "<B>Form:</B> Liquid",
                    "<B>State of Readiness:</B> Ready to Drink",
                    "<B>Package Quantity:</B> 8",
                    "<B>Package type:</B> Multi-Pack Single Servings",
                    "<B>Carbonation type 1:</B> Carbonated",
                    "<B>Water enhancement type 1:</B> Sparkling",
                    "<B>Net weight:</B> 96 fl oz (US)"
                  ],
                  "soft_bullets": {
                    "bullets": [
                      "Awaken your taste buds with the flavor of AHA Orange + Grapefruit sparkling water",
                      "Whether you call it seltzer, carbonated water, or fizzy beverage, AHA\u2019s bold flavor pairings offer a unique sparkling water experience that will satisfy your thirst",
                      "No sodium, no sweeteners, no calories",
                      "12 FL OZ per can",
                      "One of eight unique flavor duos"
                    ]
                  }
                },
                "product_vendors": [
                  {
                    "vendor_name": "COCACOLA GREAT PLAINS",
                    "id": "3648087"
                  },
                  {
                    "vendor_name": "Reyes Coca Cola Bottling",
                    "id": "1979595"
                  },
                  {
                    "vendor_name": "COCACOLA OZARKS/DRPEPPER",
                    "id": "1034868"
                  },
                  {
                    "vendor_name": "COCACOLA JEFFERSON",
                    "id": "1031337"
                  },
                  {
                    "vendor_name": "COCACOLA DURANGO",
                    "id": "1026407"
                  },
                  {
                    "vendor_name": "COCACOLA TULLAHOMA",
                    "id": "1018800"
                  },
                  {
                    "vendor_name": "COCACOLA LUFKIN",
                    "id": "1018321"
                  },
                  {
                    "vendor_name": "COCACOLA W KENTUCKY",
                    "id": "1017953"
                  }
                ],
                "fulfillment": {},
                "primary_brand": {
                  "canonical_url": "/b/aha/-/N-q643lenc9dx",
                  "name": "AHA",
                  "facet_id": "q643lenc9dx"
                }
              },
              "promotions": [],
              "price": {
                "current_retail": 3.69,
                "formatted_current_price": "$3.69",
                "formatted_current_price_type": "reg"
              },
              "ratings_and_reviews": {
                "statistics": {
                  "rating": {
                    "average": 4.29,
                    "count": 31,
                    "secondary_averages": [
                      {
                        "id": "Quality",
                        "label": "quality",
                        "value": 3.85
                      },
                      {
                        "id": "Value",
                        "label": "value",
                        "value": 4.0
                      },
                      {
                        "id": "Taste",
                        "label": "taste",
                        "value": 3.69
                      }
                    ]
                  }
                }
              }
            },
            {
              "__typename": "ProductSummary",
              "tcin": "78098798",
              "original_tcin": "78098798",
              "item": {
                "relationship_type": "Stand Alone",
                "relationship_type_code": "SA",
                "merchandise_classification": {
                  "class_id": 4,
                  "department_id": 284
                },
                "eligibility_rules": {
                  "scheduled_delivery": {
                    "is_active": true
                  }
                },
                "enrichment": {
                  "buy_url": "https://www.target.com/p/pulp-free-100-orange-juice-from-concentrate-w-calcium-38-vitamin-d-64-fl-oz-good-38-gather-8482/-/A-78098798",
                  "images": {
                    "primary_image_url": "https://target.scene7.com/is/image/Target/GUEST_92aa81cf-cd49-42d9-810f-4a56bb7a330d",
                    "alternate_image_urls": [
                      "https://target.scene7.com/is/image/Target/GUEST_2e0e9240-d6cf-4381-92c9-1dae40a30317",
                      "https://target.scene7.com/is/image/Target/GUEST_4d2f44cc-8b9a-443c-a014-5f442324bf64"
                    ]
                  },
                  "videos": [
                    {
                      "is_list_page_eligible": false,
                      "video_files": [
                        {
                          "mime_type": "video/mp4",
                          "video_url": "https://target.scene7.com/is/content/Target/GUEST_aeff3e1d-6e9c-4510-8b18-cc47e903aeba_Flash9_Autox720p_2600k"
                        }
                      ]
                    }
                  ]
                },
                "dpci": "284-04-0421",
                "cart_add_on_threshold": 35.0,
                "product_description": {
                  "title": "Pulp Free 100% Orange Juice From Concentrate w/ Calcium &#38; Vitamin D - 64 fl oz - Good &#38; Gather&#8482;",
                  "bullet_descriptions": [
                    "<B>Contains:</B> Does Not Contain Any of the 8 Major Allergens",
                    "<B>Form:</B> Liquid",
                    "<B>State of Readiness:</B> Ready to Drink",
                    "<B>Package Quantity:</B> 1",
                    "<B>Package type:</B> Individual Item Multi-Serving",
                    "<B>Percentage juice content 1:</B> 100",
                    "<B>Pulp content 1:</B> No Pulp",
                    "<B>Net weight:</B> 64 fl oz (US)",
                    "<B>Beverage container material:</B> Paper or Cardboard"
                  ],
                  "soft_bullets": {
                    "bullets": [
                      "64fl oz of premium 100% orange juice is a must-have fridge staple",
                      "Pulp-free and from concentrate for full and smooth flavor",
                      "Excellent source of vitamin C for feel-good sipping",
                      "No preservatives, artificial flavors or colors for the perfect citrus taste",
                      "Keep chilled in the refrigerator for a refreshing morning beverage"
                    ]
                  }
                },
                "product_vendors": [
                  {
                    "vendor_name": "COUNTRY PURE FOODS INC",
                    "id": "1290097"
                  },
                  {
                    "vendor_name": "C & S HAWAII",
                    "id": "1256646"
                  },
                  {
                    "vendor_name": "STORE ONLY DUMMY VENDOR",
                    "id": "1213676"
                  },
                  {
                    "vendor_name": "C & S WHOLESALE GROCERS",
                    "id": "1142161"
                  }
                ],
                "fulfillment": {},
                "primary_brand": {
                  "canonical_url": "/b/good-gather/-/N-yfqzk",
                  "name": "Good & Gather",
                  "facet_id": "yfqzk"
                },
                "ribbons": [
                  "Only At Target"
                ]
              },
              "promotions": [],
              "price": {
                "current_retail": 2.79,
                "formatted_current_price": "$2.79",
                "formatted_current_price_type": "reg"
              },
              "ratings_and_reviews": {
                "statistics": {
                  "rating": {
                    "average": 4.0,
                    "count": 30,
                    "secondary_averages": [
                      {
                        "id": "Taste",
                        "label": "taste",
                        "value": 3.86
                      },
                      {
                        "id": "Value",
                        "label": "value",
                        "value": 4.5
                      },
                      {
                        "id": "Quality",
                        "label": "quality",
                        "value": 4.0
                      }
                    ]
                  }
                }
              }
            },
            {
              "__typename": "ProductSummary",
              "tcin": "14589348",
              "original_tcin": "14589348",
              "item": {
                "relationship_type": "Stand Alone",
                "relationship_type_code": "SA",
                "merchandise_classification": {
                  "class_id": 12,
                  "department_id": 212
                },
                "eligibility_rules": {
                  "add_on": {
                    "is_active": true
                  },
                  "scheduled_delivery": {
                    "is_active": true
                  }
                },
                "enrichment": {
                  "buy_url": "https://www.target.com/p/no-sugar-added-mandarin-oranges-15oz-market-pantry-8482/-/A-14589348",
                  "images": {
                    "primary_image_url": "https://target.scene7.com/is/image/Target/GUEST_acd854fb-59fd-4e92-bddc-1533d2fa651f"
                  }
                },
                "dpci": "212-12-0461",
                "cart_add_on_threshold": 35.0,
                "product_description": {
                  "title": "No Sugar Added Mandarin Oranges 15oz - Market Pantry&#8482;",
                  "bullet_descriptions": [
                    "<B>Contains:</B> Does Not Contain Any of the 8 Major Allergens",
                    "<B>Features:</B> No Added Sugar",
                    "<B>Form:</B> Pieces",
                    "<B>State of Readiness:</B> Ready to Eat",
                    "<B>Package Quantity:</B> 1",
                    "<B>Package type:</B> Individual Item Multi-Serving",
                    "<B>Net weight:</B> 15  Ounces",
                    "<B>Country of Origin:</B> China"
                  ],
                  "soft_bullets": {
                    "bullets": [
                      "Convenient 3.5 serving, 15oz can",
                      "No sugar added",
                      "Good source of Vitamin C"
                    ]
                  }
                },
                "product_vendors": [
                  {
                    "vendor_name": "DEL MONTE FOODS INC",
                    "id": "1312645"
                  },
                  {
                    "vendor_name": "VIMPEX INTERNATIONAL",
                    "id": "1297472"
                  }
                ],
                "fulfillment": {},
                "primary_brand": {
                  "canonical_url": "/b/market-pantry/-/N-85ceg",
                  "name": "Market Pantry",
                  "facet_id": "85ceg"
                },
                "ribbons": [
                  "Only At Target"
                ]
              },
              "promotions": [],
              "price": {
                "current_retail": 0.99,
                "formatted_current_price": "$0.99",
                "formatted_current_price_type": "reg"
              },
              "ratings_and_reviews": {
                "statistics": {
                  "rating": {
                    "average": 4.38,
                    "count": 34,
                    "secondary_averages": [
                      {
                        "id": "Quality",
                        "label": "quality",
                        "value": 4.25
                      },
                      {
                        "id": "Taste",
                        "label": "taste",
                        "value": 4.13
                      },
                      {
                        "id": "Value",
                        "label": "value",
                        "value": 4.0
                      }
                    ]
                  }
                }
              }
            },
            {
              "__typename": "ProductSummary",
              "tcin": "78098797",
              "original_tcin": "78098797",
              "item": {
                "relationship_type": "Stand Alone",
                "relationship_type_code": "SA",
                "merchandise_classification": {
                  "class_id": 4,
                  "department_id": 284
                },
                "eligibility_rules": {
                  "scheduled_delivery": {
                    "is_active": true
                  }
                },
                "enrichment": {
                  "buy_url": "https://www.target.com/p/pulp-free-100-orange-juice-from-concentrate-64-fl-oz-good-38-gather-8482/-/A-78098797",
                  "images": {
                    "primary_image_url": "https://target.scene7.com/is/image/Target/GUEST_6b303314-3551-4eb7-acd0-dd5dcb58f78d",
                    "alternate_image_urls": [
                      "https://target.scene7.com/is/image/Target/GUEST_c5af6712-dd93-4cc0-b031-4803817c7fa3",
                      "https://target.scene7.com/is/image/Target/GUEST_4ad52bab-2cdf-4d7e-a165-611eb05d7c32"
                    ]
                  }
                },
                "dpci": "284-04-0420",
                "cart_add_on_threshold": 35.0,
                "product_description": {
                  "title": "Pulp Free 100% Orange Juice From Concentrate - 64 fl oz - Good &#38; Gather&#8482;",
                  "bullet_descriptions": [
                    "<B>Contains:</B> Does Not Contain Any of the 8 Major Allergens",
                    "<B>Form:</B> Liquid",
                    "<B>State of Readiness:</B> Ready to Drink",
                    "<B>Package Quantity:</B> 1",
                    "<B>Package type:</B> Individual Item Multi-Serving",
                    "<B>Percentage juice content 1:</B> 100",
                    "<B>Pulp content 1:</B> No Pulp",
                    "<B>Net weight:</B> 64 fl oz (US)",
                    "<B>Beverage container material:</B> Paper or Cardboard"
                  ],
                  "soft_bullets": {
                    "bullets": [
                      "64fl oz of premium 100% orange juice is a must-have fridge staple",
                      "Pulp-free and from concentrate for full and smooth flavor",
                      "Excellent source of vitamin C for feel-good sipping",
                      "No preservatives, artificial flavors or colors for the perfect citrus taste",
                      "Keep chilled in the refrigerator for a refreshing morning beverage"
                    ]
                  }
                },
                "product_vendors": [
                  {
                    "vendor_name": "COUNTRY PURE FOODS INC",
                    "id": "1290097"
                  },
                  {
                    "vendor_name": "C & S HAWAII",
                    "id": "1256646"
                  },
                  {
                    "vendor_name": "STORE ONLY DUMMY VENDOR",
                    "id": "1213676"
                  },
                  {
                    "vendor_name": "C & S WHOLESALE GROCERS",
                    "id": "1142161"
                  }
                ],
                "fulfillment": {},
                "primary_brand": {
                  "canonical_url": "/b/good-gather/-/N-yfqzk",
                  "name": "Good & Gather",
                  "facet_id": "yfqzk"
                },
                "ribbons": [
                  "Only At Target"
                ]
              },
              "promotions": [],
              "price": {
                "current_retail": 2.79,
                "formatted_current_price": "$2.79",
                "formatted_current_price_type": "reg"
              },
              "ratings_and_reviews": {
                "statistics": {
                  "rating": {
                    "average": 3.94,
                    "count": 17,
                    "secondary_averages": [
                      {
                        "id": "Taste",
                        "label": "taste",
                        "value": 3.88
                      },
                      {
                        "id": "Value",
                        "label": "value",
                        "value": 4.5
                      },
                      {
                        "id": "Quality",
                        "label": "quality",
                        "value": 3.75
                      }
                    ]
                  }
                }
              }
            },
            {
              "__typename": "ProductSummary",
              "tcin": "13231219",
              "original_tcin": "13231219",
              "item": {
                "relationship_type": "Stand Alone",
                "relationship_type_code": "SA",
                "merchandise_classification": {
                  "class_id": 12,
                  "department_id": 212
                },
                "eligibility_rules": {
                  "add_on": {
                    "is_active": true
                  },
                  "scheduled_delivery": {
                    "is_active": true
                  }
                },
                "enrichment": {
                  "buy_url": "https://www.target.com/p/del-monte-no-sugar-added-mandarin-oranges-in-water-15oz/-/A-13231219",
                  "images": {
                    "primary_image_url": "https://target.scene7.com/is/image/Target/GUEST_83e8bae9-2b8e-4eeb-9d7a-a6c8db0f4cfc"
                  }
                },
                "dpci": "212-12-0429",
                "cart_add_on_threshold": 35.0,
                "product_description": {
                  "title": "Del Monte No Sugar Added Mandarin Oranges in Water 15oz",
                  "bullet_descriptions": [
                    "<B>Contains:</B> Does Not Contain Any of the 8 Major Allergens",
                    "<B>Features:</B> No Added Sugar, No Preservatives",
                    "<B>Form:</B> Sliced",
                    "<B>State of Readiness:</B> Ready to Eat",
                    "<B>Package Quantity:</B> 1",
                    "<B>Package type:</B> Individual Item Multi-Serving",
                    "<B>Net weight:</B> 15 Ounces",
                    "<B>Country of Origin:</B> China"
                  ],
                  "soft_bullets": {
                    "bullets": [
                      "DELICIOUS ORANGE SLICES: These canned orange segments are immersed in water, picked at the peak of freshness. The fruit can be eaten on-the-go and can be used with all your favorite recipes.",
                      "NUTRITIOUS & DELICIOUS: All natural fruit slices are packed in water, artificially sweetened that is bursting with delicious flavor.",
                      "NO PRESERVATIVES: With no added sugar, these Del Monte mandarin orange pieces are the perfect choice right out of the can.",
                      "HEALTHY TREAT: These orange slices contain dietary fiber and are packed with Vitamin C, to add delight to your day, whether you are at home or the office.",
                      "PREMIUM QUALITY: Del Monte has earned a reputation with innovations and dedication to quality. We strive to cultivate the best vegetables and fruits, working with family farmers, to help your family live a life full of vitality and enjoyment."
                    ]
                  }
                },
                "product_vendors": [
                  {
                    "vendor_name": "THE J.M. SMUCKER CO",
                    "id": "2557315"
                  },
                  {
                    "vendor_name": "DEL MONTE FOODS INC",
                    "id": "1312645"
                  }
                ],
                "fulfillment": {},
                "primary_brand": {
                  "canonical_url": "/b/del-monte/-/N-56f77",
                  "name": "Del Monte",
                  "facet_id": "56f77"
                }
              },
              "promotions": [],
              "price": {
                "current_retail": 1.89,
                "formatted_current_price": "$1.89",
                "formatted_current_price_type": "reg"
              },
              "ratings_and_reviews": {
                "statistics": {
                  "rating": {
                    "average": 4.68,
                    "count": 142,
                    "secondary_averages": [
                      {
                        "id": "Value",
                        "label": "value",
                        "value": 3.33
                      },
                      {
                        "id": "Taste",
                        "label": "taste",
                        "value": 4.0
                      },
                      {
                        "id": "Quality",
                        "label": "quality",
                        "value": 4.0
                      }
                    ]
                  }
                }
              }
            },
            {
              "__typename": "ProductSummary",
              "tcin": "79700406",
              "original_tcin": "79700406",
              "item": {
                "relationship_type": "Stand Alone",
                "relationship_type_code": "SA",
                "merchandise_classification": {
                  "class_id": 8,
                  "department_id": 211
                },
                "eligibility_rules": {
                  "add_on": {
                    "is_active": false
                  },
                  "scheduled_delivery": {
                    "is_active": true
                  }
                },
                "enrichment": {
                  "buy_url": "https://www.target.com/p/suja-organic-sparkling-juice-pineapple-orange-12-fl-oz/-/A-79700406",
                  "images": {
                    "primary_image_url": "https://target.scene7.com/is/image/Target/GUEST_1f10dda7-ce32-4c39-9050-196282448cf1",
                    "alternate_image_urls": [
                      "https://target.scene7.com/is/image/Target/GUEST_65687082-5406-4184-b5ce-9b423818fa06",
                      "https://target.scene7.com/is/image/Target/GUEST_948ae060-d048-4523-8cc5-df5fb1d38d02"
                    ]
                  }
                },
                "dpci": "211-08-3250",
                "cart_add_on_threshold": 35.0,
                "product_description": {
                  "title": "Suja Organic Sparkling Juice Pineapple Orange - 12 fl oz",
                  "bullet_descriptions": [
                    "<B>Contains:</B> Does Not Contain Any of the 8 Major Allergens",
                    "<B>Form:</B> Liquid",
                    "<B>State of Readiness:</B> Ready to Drink",
                    "<B>Package Quantity:</B> 1",
                    "<B>Package type:</B> Individual Item Single Serving",
                    "<B>Percentage juice content 1:</B> 41",
                    "<B>Net weight:</B> 12 fl oz (US)"
                  ],
                  "soft_bullets": {
                    "bullets": [
                      "USDA Certified Organic",
                      "Non-GMO Project Verified",
                      "Vegan",
                      "Kosher certified",
                      "Dairy-free, soy-free, gluten-free",
                      "No preservatives or added flavors"
                    ]
                  }
                },
                "product_vendors": [
                  {
                    "vendor_name": "SUJA LIFE LLC",
                    "id": "1315037"
                  },
                  {
                    "vendor_name": "C & S WHOLESALE GROCERS",
                    "id": "1142161"
                  }
                ],
                "fulfillment": {},
                "primary_brand": {
                  "canonical_url": "/b/suja/-/N-4yj9y",
                  "name": "Suja",
                  "facet_id": "4yj9y"
                }
              },
              "promotions": [],
              "price": {
                "current_retail": 3.99,
                "formatted_current_price": "$3.99",
                "formatted_current_price_type": "reg"
              },
              "ratings_and_reviews": {
                "statistics": {
                  "rating": {
                    "average": 3.5,
                    "count": 8,
                    "secondary_averages": [
                      {
                        "id": "Quality",
                        "label": "quality",
                        "value": 4.5
                      },
                      {
                        "id": "Taste",
                        "label": "taste",
                        "value": 4.75
                      },
                      {
                        "id": "Value",
                        "label": "value",
                        "value": 2.75
                      }
                    ]
                  }
                }
              }
            }
          ]
        }
      }
    }
```
