# app-localiza-vagas

## Pré-requisitos
- mysql
- node

## Instruções para execução
- Execute `sql-create.sql` para criar o banco e as tabelas. Assumimos (para fins de teste) que o usuário e senha sejam root. (estes valores estão hardcoded em cada um dos arquivos em `./node/services/`)
```
mysql -u root
source sql-create.sql
```
- Execute `./node/index.js` para criar o servidor e popular alguns usuários.
```
node ./node/index.js
```
- Execute `populate-db.sql` para popular outras tabelas do banco de dados.
- Abra o projeto no Android Studio e ajuste a constante `API_ADDRESS` da classe `Service` (pacote `Network`) com o seu IP atual.
- Execute o aplicativo.
