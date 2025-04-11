package com.codewithantonio.Product.Api.services;

import com.codewithantonio.Product.Api.domain.usuario.*;

public interface AuthenticationService {
    LoginResponseDTO login(AuthenticationDTO data);
    RegisterResponseDTO register(RegisterDTO data);
}