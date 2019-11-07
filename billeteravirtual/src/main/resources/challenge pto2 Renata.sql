SELECT billetera.billetera_id, cuenta.cuenta_id, SUM(movimiento.importe) AS saldo_real
FROM movimiento
JOIN cuenta ON movimiento.cuenta_id = cuenta.cuenta_id
JOIN billetera ON cuenta.billetera_id = billetera.billetera_id AND billetera.billetera_id = 33;