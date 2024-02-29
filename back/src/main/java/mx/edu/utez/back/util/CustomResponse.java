package mx.edu.utez.back.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomResponse<T>{
    T data;
    boolean error;
    int statusCode;
    String message;
}
