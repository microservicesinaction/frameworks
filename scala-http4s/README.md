This is your Play application
=============================

To run tests
-------------

TBD

To run
-------

sbt run

Endpoints
==========

GET /items/
returns all items that match criteria

GET /items/{id}
returns item with requested id

PUT /items/{id}
Example Request Payload:
{
  "id": "1",
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

registers or updates item with requested id. Id in body is ignored.

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

deletes item with requested id

