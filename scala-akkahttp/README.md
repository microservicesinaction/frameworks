This is your Akka-Http application
=============================

To run tests
-------------

sbt clean test

To run
-------

sbt run

Endpoints
==========

GET /items?details=true/false?start=""?size=0
returns all items that match criteria

GET /items/{id}
returns item with requested id

PUT /items/{id}
Example Request Payload:
{
  "id": 1,
  "price": {
    "value": 12,
    "currency": "GBP"
   },
  "colour": "colour",
  "tags": "tags",
  "size": {
    "value": 100,
    "region": "EU"
  },
  "state": "state",
  "sku": "sku"
}

registers or updates item with requested id

PUT /stock/{itemId}
Example Request Payload:
{
  "quantities": {
    "count": 12,
    "state": "Full"
   },
  "sku": "102/305"
}

adds a stock record to the database - just to test for now


POST /items
Example Request Payload:
{
  "id": 1,
  "price": {
    "value": 12,
    "currency": "GBP"
   },
  "colour": "colour",
  "tags": "tags",
  "size": {
    "value": 100,
    "region": "EU"
  },
  "state": "state",
  "sku": "sku"
}

registers new item (id doesn't mean anything)

DELETE /items/{id}

deletes item and corresponding stock with requested id