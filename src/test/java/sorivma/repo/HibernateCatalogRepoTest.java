package sorivma.repo;

import org.junit.jupiter.api.Test;

class HibernateCatalogRepoTest {

    @Test
    void getProducts() {
        HibernateCatalogRepo catalogRepo = new HibernateCatalogRepo();
        System.out.println(catalogRepo.getProducts());
    }
}