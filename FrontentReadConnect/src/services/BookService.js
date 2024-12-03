import http from "../http-common.js";

class BookService {

    postNewBook(writerId, data) {
        return http.post(`/books/create?writerId=${writerId}`, data, {
            headers: {
                'Content-Type': 'multipart/form-data' 
            }
        });
    }

    getBookByUserId(userId){
        return http.get(`/books/user/${userId}`);
    }

    getAllBooks(){
        return http.get(`/books`);
    }

    getBookById(id){
        return http.get(`/books/${id}`);
    }

    getComments(bookId) {
        return http.get(`/books/${bookId}/comments`);
    }

    addComment(bookId, userId, content) {
        return http.post(`/books/${bookId}/comment`, null, {
            params: { userId, content },
        });
    }

    getCommentsMetadata(bookId) {
        return http.get(`/comments/metadata/${bookId}`); 
    }
      

    rateBook(bookId, rating, userId) {
        return http.post(`/books/${bookId}/rate`, null, {
            params: { userId, rating },
        });
    }
    

}

export default new BookService();
