# STONKSimulator API

API that simulates personal stock market wallet. You can buy and sell stocks with initial 100k balance in your wallet.
Stocks prices are gather from [Financial Modeling Prep Api](https://financialmodelingprep.com/developer).

By design there is separation between user and wallet entities. For future expansion open position should be separated
from wallet. There should also be CRUD endpoints just for admin, because at this moment admin has no special permissions
over user.

#### Endpoints

Postman collection: https://www.getpostman.com/collections/a6492c3bc11495ea9a91 or in file in repository.

- `POST localhost:9000/register` - username, password
- `POST localhost:9000/login` - username, password
- `GET localhost:9000/profile`
- `GET localhost:9000/wallet`
- `POST localhost:9000/wallet/buy` - stock_symbol, volume
- `GET localhost:9000/wallet/sell/{id}`