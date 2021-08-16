# zooplus-petstore-test project


Clone the repo:

```bash
$ git clone git@github.com:adhir39/zooplus-petstore-test.git
```

```bash
$ cd zooplus-petstore-test
```

Run the project with java 16

```bash
$ ./mvnw clean test allure:report
```

```bash
$ ./mvnw allure:serve
```


To test the Pet store project we encountered some unclear requirement or docuemntation, hence we are assuming some basic criteria to test the API

1. where ever we don't see any documentation about failure , we assume it is http 400
2. The API doesnt say which fields are mandatory which makes testing some API tricky. For some fields like "name","id" we assumed they are mandatory and cant be null.
3. for Uploading the image for a pet we dont know what file type the service should accept and for simplicity, we assumed only JPEG/PNG files could be uploaded with 100kb max file size.
4. For API findPetByStatus it has no page limit which can cause big chunk of data as response.
