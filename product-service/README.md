
## REST API Endpoints

All inputs and outputs use JSON format.

```
/product
  GET / - List of products
  POST / - Add product - required : String name , String groupId, String userId
  GET /{id} - View product
  POST /{id} - Update product
  GET /{id}/images - View product images
  GET /image/{id}- View image
  POST /{id}/uploadimage - Upload product image

/group
  GET / - List of groups
  POST / - Add group
  GET /{id} - View group
  POST /{id} - Update group
```