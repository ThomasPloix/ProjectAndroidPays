# Application Mobile Android pour la Recherche de Pays #

## Description :
Cette application mobile Android, écrite en Kotlin, permet aux utilisateurs de rechercher des pays en utilisant une chaîne de caractères (partie du nom de la capitale ou du nom du pays). 
Les résultats de la recherche sont affichés dans une liste de pays avec leurs drapeaux, leurs noms et leurs capital. Les utilisateurs peuvent également afficher la fiche d'un pays et mettre certains pays en favoris pour pouvoir les visualiser sans connexion. 
Les données concernant les pays sont accessibles via l'API Rest suivante : https://restcountries.com/.

## Installation :

Clonez le référentiel du projet sur votre machine locale.
Ouvrez le projet dans Android Studio.
Assurez-vous que vous avez la dernière version du SDK Android et du plugin Kotlin installées (API 34).
Lancez l'application sur un appareil Android ou un émulateur.

## Utilisation :
L'application dispose d'une interface utilisateur simple et intuitive.

Tout d'abord 2 boutons qui constituent le menu principal : 
- La recherche des pays
- Le jeu Bonus

### Première Partie: Recherche des Pays

Appuyer sur le bouton en haut à droite pour faire appel à l'api. Cela permet de récupérer toutes les informations des pays.

Pour rechercher un pays, saisissez une chaîne de caractères dans le champ de recherche.
Les résultats de la recherche seront affichés dans une liste de pays avec leurs drapeaux, leurs noms et leur capitale.
Pour afficher la fiche d'un pays, appuyez sur l'élément de la liste correspondant au pays.
Pour mettre un pays en favori, appuyez sur le bouton dans la liste des pays (le bouton devient ainsi noir).
Les pays en favoris peuvent être visualisés lors du lancement de l'application si l'appel API ne fonctionne pas.

### Deuxième Partie: JeuBonus

En termes de fonctionnalités bonus, nous avons décidé de développer un jeu où le but est de deviner un pays sélectionné aléatoirement.
Tout d'abord il faut s'assurer de la bonne synchronisation avec l'API. Cette dernière sera confirmée par la disparition des 2 cases d'exemple.

La jeu fonctionne de la manière suivante : 
- Selection d'un pays comme tentative (en appuyant sur les élements filtrés ou en appuyant sur entrée pour sélectionner le premier item).
- Analyse des réponses données par le logiciel :

(Rouge = Pas du tout ça, Orange = Première lettre pareil avec le pays à deviner, ou langue parlée partiellement, Vert = bonne réponse)
- Indice potentiel donné par le jeu (à 4, 6, 8 et 10 essais)
- Fin de la partie (si 12 tentatives ou trouvé avant)


## Authors
Si vous avez des questions ou des préoccupations concernant ce projet, veuillez nous contacter à :

- [@Fabrikot](https://github.com/Fabrikot)
- [@ThomasPloix](https://github.com/ThomasPloix)

