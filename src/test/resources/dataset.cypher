start root=node(0)  
create (Cher{name:'Cher'}),(PasCher{name:'PasCher'}),(Jaune{name:'Jaune'}),(Noir{name:'Noir'}),
(SacHermes{name:'SacHermes'}), (EscarppinsJinny{name:'EscarppinsJinny'}), (ShoppingCart1{name:'ShoppingCart1'}), (ShoppingCart2{name:'ShoppingCart2'}), (Date15_01_2012{name:'Date15_01_2012'}), (Matin{name:'Matin'}), (Lundi{name:'Lundi'}),
ShoppingCart1-[:contient]->SacHermes, ShoppingCart1-[:contient]->EscarppinsJinny,
ShoppingCart2-[:contient]->EscarppinsJinny,
SacHermes-[:coute]->Cher, EscarppinsJinny-[:coute]->PasCher,
SacHermes-[:couleur]->Jaune, EscarppinsJinny-[:couleur]->Noir,
ShoppingCart1-[:validerLe]->Date15_01_2012, ShoppingCart2-[:validerLe]->Date15_01_2012,
ShoppingCart1-[:validerLeJour]->Lundi, ShoppingCart2-[:validerLeJour]->Mardi
