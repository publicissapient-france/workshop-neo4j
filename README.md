Hand's On Neo4j
=========

![Mou icon](http://upload.wikimedia.org/wikipedia/en/4/4a/Neo4j.jpg)

Nous sommes une boutique de e-commerce fleurissante, et afin de mieux cerner notre clientèle et ses besoins, le service marketing souhaite faire des analyses sur les stats du site.
Etant un pros de la Business Intelligence, on fait appel à vous...

Pour ce faire vous devrez implémenter diverses méthodes qui permettront d'analyser les stats enregistrées par le site dans une base de données Neo4J.

Pour vous entrainer on vous met à disposition un environnement de test avec des data déjà prêtes.
Vous pouvez d'ailleurs visualiser le graphe <http://www.google.com> ***TODO mettre la bon url***


### La recommandation de produit

La première chose qui saute au yeux quand l'on arrive sur le site, c'est qu'il n'y pas de recommandation de produit. Aussi on vous demande avant toute chose d'implémenter une méthode permettant à partir d'un nom de produit de retrouver tous les produits qui ont été acheté dans le même panier que ce dernier. Didier votre collègue vous suggère d'utiliser les requêtes `Cypher`, directement dans la console Neo4j.

Une fois votre requête prête vous pourrez implémenter son execution dans la méthode

	GraphDAO.getRecommendedProductsFor

Documentation relative à cette partie : <http://docs.neo4j.org/chunked/stable/tutorials-cypher-java.html>


### Le début de la Business Intelligence

Une fois cette aparté close, on vous demande de commencer à implémenter les méthodes de Businesss intelligence à proprement parler.
Nous commencerons donc par vouloir connaitre le nombre de produit vendu à une date donnée, selon une couleur et un modèle bien précis.

	GraphDAO.getNumberOfSales

La bible `Cypher` que vous avez sur votre table de nuit : <http://docs.neo4j.org/chunked/stable/cypher-query-lang.html>

### Quels sont les clients les plus influants ?

C'est le cinquième anniversaire de création de la boite, et pour fêter l'évènement, votre entreprise a décidé de récompenser les clients qui ont ramené le plus de client via le parainage.
On fait donc encore appel à vous pour qu'à partir d'un nom de client on puisse récupérer tous les clients qu'il a parrainé ainsi que le filleul et ce de façon récursive.

En parlant de cette nouvelle tâche avec Didier au café, celui-ci vous parle des `Traverser`, un Objet de l'API Neo4j qui serait capable de parcourir facilement un graphe en fonction d'un type de relation ainsi que du sens de cette relation.
Il vous donne même un lien sur la doc : <http://docs.neo4j.org/chunked/stable/tutorial-traversal-java-api.html>

	GraphDAO.getRecursiveSponsoredClient


### Parrainage

Il y a un bug dans le mécanisme des parrainages. Nous décidons de faire table rase et de recoder la méthode qui permet à un client de parrainer un filleul.

    GraphDAO.addSponsoredClient

Didier (qui connait décidément bien les bases graphes :) vous parle du mécanisme d'indexation automatique fourni par Neo4J et vous conseille la doc <http://docs.neo4j.org/chunked/milestone/auto-indexing.html>
