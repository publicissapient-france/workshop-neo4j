create
    (SacHermes:Product{name:'SacHermes'}),
    (EscarppinsJinny:Product{name:'EscarppinsJinny'}),
    (SacLouisVitton:Product{name:'SacLouisVitton'}),
    (ChaussureLouboutin:Product{name:'ChaussureLouboutin'}),
    
    (Cher{name:'Cher'}),
    (PasCher{name:'PasCher'}),

    (Jaune{name:'Jaune'}),
    (Noir{name:'Noir'}),
    (Marron{name:'Marron'}),
    
    (ShoppingCart1:ShoppingCart{name:'ShoppingCart1'}),
    (ShoppingCart2:ShoppingCart{name:'ShoppingCart2'}),
    (ShoppingCart3:ShoppingCart{name:'ShoppingCart3'}),

    (Date15_01_2012{name:'Date15_01_2012'}),
    (Date02_01_2000{name:'Date02_01_2000'}),

    (Lundi{name:'Lundi'}),

    (Client1:Client{name:'client1'}),
    (Client2:Client{name:'client2'}),
    (Client3:Client{name:'client3'}),
    (Client4:Client{name:'client4'}),
    (Client5:Client{name:'client5'}),

    (Client1-[:HAS_SPONSORED]->Client2),
    (Client2-[:HAS_SPONSORED]->Client3),
    (Client3-[:HAS_SPONSORED]->Client4),
    (Client2-[:HAS_SPONSORED]->Client5),
    (Client1-[:OWN]->ShoppingCart1),
    (Client4-[:OWN]->ShoppingCart2),

    (ShoppingCart1-[:CONTAINS]->SacHermes),
    (ShoppingCart1-[:CONTAINS]->EscarppinsJinny),

    (ShoppingCart2-[:CONTAINS]->EscarppinsJinny),
    (ShoppingCart2-[:CONTAINS]->ChaussureLouboutin),
    
    (ShoppingCart3-[:CONTAINS]->SacHermes),
    (ShoppingCart3-[:CONTAINS]->SacLouisVitton),
    (ShoppingCart3-[:CONTAINS]->ChaussureLouboutin),
    (ShoppingCart3-[:CONTAINS]->SacHermes),
    (ShoppingCart3-[:CONTAINS]->SacLouisVitton),
    (ShoppingCart3-[:CONTAINS]->ChaussureLouboutin),
    
    (ShoppingCart1-[:DATE]->Date15_01_2012),
    (ShoppingCart2-[:DATE]->Date15_01_2012),
    (ShoppingCart3-[:DATE]->Date02_01_2000),
    (ShoppingCart1-[:DAY_OF_WEEK]->Lundi),
    (ShoppingCart2-[:DAY_OF_WEEK]->Mardi)