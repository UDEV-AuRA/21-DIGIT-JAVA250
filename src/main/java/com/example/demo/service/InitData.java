package com.example.demo.service;

import com.example.demo.entity.Article;
import com.example.demo.entity.Client;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

/**
 * Classe permettant d'insérer des données dans l'application.
 */
@Service
@Transactional
public class InitData implements ApplicationListener<ApplicationReadyEvent> {

    private EntityManager entityManager;

    public InitData(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        insertTestData();
    }

    private void insertTestData() {
        Article a1 = createArticle("Chargeurs; de téléphones Portables", 22.98, 9);
        Article a2 = createArticle("Playmobil Hydravion de Police", 14.39, 2);
        Article a3 = createArticle("Distributeur de croquettes pour chien", 12.99, 0);

        Client cl1 = createClient("John", "Doe");
    }

    private Client createClient(String prenom, String nom) {
        Client client = new Client();
        client.setPrenom(prenom);
        client.setNom(nom);
        entityManager.persist(client);
        return null;
    }

    private Article createArticle(String libelle, double prix, int stock) {
        Article a1 = new Article();
        a1.setLibelle(libelle);
        a1.setPrix(prix);
        a1.setStock(stock);
        entityManager.persist(a1);
        return a1;
    }

}
