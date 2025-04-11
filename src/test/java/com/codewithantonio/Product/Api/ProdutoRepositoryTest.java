package com.codewithantonio.Product.Api;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;


@DataJpaTest
@ActiveProfiles("test")
public class ProdutoRepositoryTest {

    @Test
    void findProductBtId(){

    }
}
