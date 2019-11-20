SELECT usuario.username, persona.nombre, billetera.billetera_id, cuenta.cuenta_id, cuenta.saldo
FROM usuario
JOIN persona ON usuario.persona_id = persona.persona_id
JOIN billetera ON persona.persona_id = billetera.persona_id
JOIN cuenta ON billetera.billetera_id = cuenta.billetera_id;
