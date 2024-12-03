package br.com.bea.screenmatch.principal;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import br.com.bea.screenmatch.model.DadosSerie;
import br.com.bea.screenmatch.model.DadosTemporada;
import br.com.bea.screenmatch.service.ConsumoApi;
import br.com.bea.screenmatch.service.ConverteDados;

public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String APIKEY = "&apikey=99917be4";

    public void exibeMenu() {
        System.out.println("Digite o nome da série para busca");
        var nomeSerie = leitura.nextLine();
        var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + APIKEY);
        // "https://www.omdbapi.com/?t=Supernatural&apikey=99917be4"
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        System.out.println(dados);

        List<DadosTemporada> temporadas = new ArrayList<>();

        for (int i = 1; i <= dados.totalTemporadas(); i++) {
            json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + "&Season=" + i + APIKEY);
            DadosTemporada dadosTemporada = conversor.obterDados(json,
                    DadosTemporada.class);
            temporadas.add(dadosTemporada);
        }

        // for (int i = 0; i < dados.totalTemporadas(); i++) {
        // List<DadosEpisodio> episodiosTemporada = temporadas.get(i).episodios();
        // for (int j = 0; j < episodiosTemporada.size(); j++) {
        // System.out.println(episodiosTemporada.get(j).titulo());
        // }

        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));
    }
}
