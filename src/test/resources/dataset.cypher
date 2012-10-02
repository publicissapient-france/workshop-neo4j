start root=node(0)  
create (Cher{name:'Cher'}),(PasCher{name:'PasCher'}),(Jaune{name:'Jaune'}),(Noir{name:'Noir'}),
(SacHermes{name:'SacHermes'}), (EscarppinsJinny{name:'EscarppinsJinny'}),
(ShoppingCart1{name:'ShoppingCart1'}), (ShoppingCart2{name:'ShoppingCart2'}),
(Date15_01_2012{name:'Date15_01_2012'}), (Matin{name:'Matin'}), (Lundi{name:'Lundi'}),
(Client1{name:'client1'}),(Client2{name:'client2'}),(Client3{name:'client3'}),(Client4{name:'client4'}), (Client5{name:'client5'}),

Client1-[:SPONSORED]->Client2, Client2-[:SPONSORED]->Client3, Client3-[:SPONSORED]->Client4, Client2-[:SPONSORED]->Client5,
Client1-[:OWN]->ShoppingCart1, Client4-[:OWN]->ShoppingCart2,
ShoppingCart1-[:CONTAINS]->SacHermes, ShoppingCart1-[:CONTAINS]->EscarppinsJinny,
ShoppingCart2-[:CONTAINS]->EscarppinsJinny,
SacHermes-[:COSTS]->Cher, EscarppinsJinny-[:COSTS]->PasCher,
SacHermes-[:COLOR]->Jaune, EscarppinsJinny-[:COLOR]->Noir,
ShoppingCart1-[:DATE]->Date15_01_2012, ShoppingCart2-[:DATE]->Date15_01_2012,
ShoppingCart1-[:DAY_OF_WEEK]->Lundi, ShoppingCart2-[:DAY_OF_WEEK]->Mardi