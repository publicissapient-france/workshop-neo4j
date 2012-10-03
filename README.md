Hand's On Neo4j
=========

![Mou icon](http://upload.wikimedia.org/wikipedia/en/4/4a/Neo4j.jpg)

Nous sommes une boutique de e-commerce fleurissante, et afin de mieux cerner notre clientèle et ces besoins, le service marketing désirais faire des analyses sur les stats du site.
Etant un pros de la Businnes Intelligence, on fait appelle à vous...

Pour ce faire vous devrez implémenter divers methodes qui permettrons d'analyser les stats enregistrés par le site dans une base de donnéees Neo4J.

Pour vous entrainer on vous met à disposition un environement de test avec des data déjà prêtes.
Vous pouvez d'ailleurs visualiser le graphe <http://www.google.com> ***TODO mettre la bon url***


### La recommandation de produit

La première chose qui saute au yeux quand l'on arrive sur le site, est qu'il n'y pas de recommandation de produit. Aussi on vous demande avant toute chose d'implémenter une méthode permettant à partir d'un nom de produit de retrouver tous les produits qui ont été acheté dans le même panier que ce dernier. Didier votre collègue vous suggère d'utiliser les requêtes `Cypher`, directement dans la console Neo4j.

Une fois votre requête prête vous pourrez implémenter son execution dans la méthode

	BiDAO.getRecommendedProductsFor

Documentation relative à cette partie : <http://docs.neo4j.org/chunked/stable/tutorials-cypher-java.html>


### Le début de la Business Intelligence

Une fois cette aparté close, on vous demande de commencer à implémenter les méthodes de Businesss intelligence à proprement parler.
Nous commencerons donc par vouloir connaitre le nombre de produit vendu à une date donnée, selon une couleur et un modèle bien précis.

	BiDAO.getNumberOfSales

La bible `Cypher` que vous avez sur votre table de nuit : <http://docs.neo4j.org/chunked/stable/cypher-query-lang.html>

### Quel sont les clients les plus influants ?

C'est le cinquième anniversaire de création de la boite, et pour fêter l'évènement votre entreprise à décider de récompenser les clients qui ont ramené le plus de client via le parainage.
On fait donc encore appelle à vous pour qu'a partir d'un nom de client on puisse récupéré tous les clients qu'il a parainné ainsi que le feuilleule et ce de façon récursive.

En parlant de cette nouvelle tâche avec Didier au café, celui-ci vous parle des `Traverser`, un Objet de l'API Neo4j qui serait capable de parcourir facilement un graph en fonction d'un type de relation ainsi que du sens de cette relation.
Il vous donne même un lien sur la doc : <http://docs.neo4j.org/chunked/stable/tutorial-traversal-java-api.html>

	BiDAO.getRecursiveSponsoredClient