# Movies App

Movies App é um aplicativo que mostra uma lista de 20 filmes, onde quando o usuário seleciona algum item da lista ele é redirecionado para a tela de detalhes, 
onde os detalhes daquele título são mostrados a ele

## Como ele foi implementado

O Movies App foi implementado em kotlin, com a arquitetura MVVM, utilizando as seguintes bibliotecas:

- Retrofit - Utilizado para o consumo de API Rest
- Moshi - Utilizado para a comunicação em JSON
- Coil - Utilizado para o carregamento de imagens
- Jetpack Navigation Component - para a navegação entre as telas

O Movies App possui uma Activity, e três fragmentos que compartilham a mesma ViewModel, elas são:

1- MoviesListFragment
2- MovieDetailFragment
3- ErrorFragment

### MoviesListFragment

Nesse fragmento é utilizado uma RecyclerView para mostrar todos os Títulos da lista obtidas pelo servidor
Quando um item da lista é selecionado o usuário é redirecionado para a MovieDetailFragment, onde seráo mostrados os detalhes do título específico
Se não for possível baixar a lista de Títulos, o fragmento tenta três vezes, até que o usuário é redirecionado a tela de erro

### MoviesDetailsFragment

O MovieDetailsFragment faz o download dos detalhes do título escolhido e mostra ao usuário. 
Assim como no MoviesListFragment, quando na primeira tentativa não é possível fazer o donload dos detalhes é feito mais três tentativas, 
após isso o usuário é redirecionado para a tela de erro

### ErrorFragment

Esse é o fragmento em que são direcionados os usuários quando se não consegue carregar os dados, nele pode-se usar um botão tentar reconectar, sendo redirecionado para a MoviesListFragment
