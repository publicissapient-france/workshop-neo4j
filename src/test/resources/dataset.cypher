start root=node(0)
create (Cher{name:'Cher'}),(PasCher{name:'PasCher'}),(Jaune{name:'Jaune'}),(Noir{name:'Noir'}),(Marron{name:'Marron'}),
(SacHermes{name:'SacHermes'}), (EscarppinsJinny{name:'EscarppinsJinny'}), (SacLouisVitton{name:'SacLouisVitton'}),
(ChaussureLouboutin{name:'ChaussureLouboutin'}),
(ShoppingCart1{name:'ShoppingCart1'}), (ShoppingCart2{name:'ShoppingCart2'}), (ShoppingCart3{name:'ShoppingCart3'}),
(Date15_01_2012{name:'Date15_01_2012'}), (Date02_01_2000{name:'Date02_01_2000'}), (Lundi{name:'Lundi'}),
(Client1{name:'client1'}), (Client2{name:'client2'}), (Client3{name:'client3'}), (Client4{name:'client4'}), (Client5{name:'client5'}),

Client1-[:HAS_SPONSORED]->Client2, Client2-[:HAS_SPONSORED]->Client3, Client3-[:HAS_SPONSORED]->Client4, Client2-[:HAS_SPONSORED]->Client5,
Client1-[:OWN]->ShoppingCart1, Client4-[:OWN]->ShoppingCart2,

ShoppingCart1-[:CONTAINS]->SacHermes, ShoppingCart1-[:CONTAINS]->EscarppinsJinny,

ShoppingCart2-[:CONTAINS]->EscarppinsJinny, ShoppingCart2-[:CONTAINS]->ChaussureLouboutin,

ShoppingCart3-[:CONTAINS]->SacHermes, ShoppingCart3-[:CONTAINS]->SacLouisVitton, ShoppingCart3-[:CONTAINS]->ChaussureLouboutin,
ShoppingCart3-[:CONTAINS]->SacHermes, ShoppingCart3-[:CONTAINS]->SacLouisVitton, ShoppingCart3-[:CONTAINS]->ChaussureLouboutin,

ShoppingCart1-[:DATE]->Date15_01_2012, ShoppingCart2-[:DATE]->Date15_01_2012, ShoppingCart3-[:DATE]->Date02_01_2000,
ShoppingCart1-[:DAY_OF_WEEK]->Lundi, ShoppingCart2-[:DAY_OF_WEEK]->Mardi