package com.beertag.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@ControllerAdvice
//@EnableWebMvc
public class ExceptionController {
    private Log logger = LogFactory.getLog(ExceptionController.class);

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ExceptionResponse> generalException (Exception ex)throws Exception{

        ExceptionResponse eR = new ExceptionResponse();
        eR.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        eR.setDescription(ex.getMessage());
        logger.error(eR.getDescription());
        System.out.println(ex);
        return new ResponseEntity<>(eR,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = BeerAlreadyAddedToTagException.class)
    public ResponseEntity<ExceptionResponse> BeerAlreadyAddedToTagException(BeerAlreadyAddedToTagException ex){
        ExceptionResponse response = new ExceptionResponse();
        response.setCode(HttpStatus.BAD_REQUEST.value());
        response.setDescription(ex.getMessage());
        logger.error(response.getDescription());
        System.out.println(ex);
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = BeerAlreadyMarkedException.class)
    public ResponseEntity<ExceptionResponse> BeerAlreadyMarkedException(BeerAlreadyMarkedException ex){
        ExceptionResponse response = new ExceptionResponse();
        response.setCode(HttpStatus.BAD_REQUEST.value());
        response.setDescription(ex.getMessage());
        logger.error(response.getDescription());
        System.out.println(ex);
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = BeerAlreadyVotedException.class)
    public ResponseEntity<ExceptionResponse> BeerAlreadyVotedException(BeerAlreadyVotedException ex){
        ExceptionResponse response = new ExceptionResponse();
        response.setCode(HttpStatus.BAD_REQUEST.value());
        response.setDescription(ex.getMessage());
        logger.error(response.getDescription());
        System.out.println(ex);
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = BeerIsNotMarkedAsWishException.class)
    public ResponseEntity<ExceptionResponse> BeerIsNotMarkedAsWishException(BeerIsNotMarkedAsWishException ex){
        ExceptionResponse response = new ExceptionResponse();
        response.setCode(HttpStatus.BAD_REQUEST.value());
        response.setDescription(ex.getMessage());
        logger.error(response.getDescription());
        System.out.println(ex);
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = DatabaseItemNotFoundException.class)
    public ResponseEntity<ExceptionResponse> DatabaseItemNotFoundException(DatabaseItemNotFoundException ex){
        ExceptionResponse response = new ExceptionResponse();
        response.setCode(HttpStatus.BAD_REQUEST.value());
        response.setDescription(ex.getMessage());
        logger.error(response.getDescription());
        System.out.println(ex);
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ItemAlreadyExistException.class)
    public ResponseEntity<ExceptionResponse> ItemAlreadyExistException(ItemAlreadyExistException ex){
        ExceptionResponse response = new ExceptionResponse();
        response.setCode(HttpStatus.BAD_REQUEST.value());
        response.setDescription(ex.getMessage());
        logger.error(response.getDescription());
        System.out.println(ex);
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = UserAlreadyExistException.class)
    public ResponseEntity<ExceptionResponse> UserAlreadyExistException(UserAlreadyExistException ex){
        ExceptionResponse response = new ExceptionResponse();
        response.setCode(HttpStatus.BAD_REQUEST.value());
        response.setDescription(ex.getMessage());
        logger.error(response.getDescription());
        System.out.println(ex);
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }
}
