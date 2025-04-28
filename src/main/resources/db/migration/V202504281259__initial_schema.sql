ALTER TABLE custo DROP FOREIGN KEY fkItemOrcamentoCusto;
ALTER TABLE custo
ADD CONSTRAINT fkItemOrcamentoCusto
FOREIGN KEY (item_orcamento_id)
REFERENCES item_orcamento(id)
ON DELETE CASCADE;