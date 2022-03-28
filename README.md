# Blog Post REST API
This is a REST API developed for my Screening Assignment for the position of Java Developer. It is developed in Spring Boot
# The resource URI are as follows:
<div>
            <h3>To add a new Post</h3>
            <p>http://localhost:8080/post/add</p>
        </div>
        <div>
            <h3>To update a Post</h3>
            <p>http://localhost:8080/post/update/{id}</p>
        </div>
        <div>
            <h3>To delete a post</h3>
            <p>http://localhost:8080/post/delete/{id}</p>
        </div>
        <div>
            <h3>To search a post</h3>
            <p>http://localhost:8080/post/search/{id}</p>
        </div>
        <div>
            <h3>Get a list of all Posts</h3>
            <p>http://localhost:8080/post/search/all</p>
          <p>http://localhost:8080/post/search/all? pageIndex= pageNumber & size = number_of_items_per_page</p>
        </div>
