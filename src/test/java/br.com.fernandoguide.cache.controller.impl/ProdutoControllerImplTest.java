package br.com.fernandoguide.cache.controller.impl;

import br.com.fernandoguide.cache.entity.Produto;
import br.com.fernandoguide.cache.mapper.ProdutoMapper;
import br.com.fernandoguide.cache.service.ProdutoService;
import br.com.fernandoguide.cache.utils.ProdutoBuilder;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class ProdutoControllerImplTest {
    private static final String ENDPOINT_URL = "/api/produto";

    @InjectMocks
    private ProdutoControllerImpl produtoController;
    @Mock
    private ProdutoService produtoService;
    @Mock
    private ProdutoMapper produtoMapper;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.produtoController).build();
    }

    @Test
    public void getAll() throws Exception {
        Mockito.when(produtoMapper.asDTOList(ArgumentMatchers.any())).thenReturn(ProdutoBuilder.getListDTO());

        Mockito.when(produtoService.findAll()).thenReturn(ProdutoBuilder.getListEntities());
        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_URL))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)));

    }

    @Test
    public void getById() throws Exception {
        Mockito.when(produtoMapper.asDTO(ArgumentMatchers.any())).thenReturn(ProdutoBuilder.getDTO());

        Mockito.when(produtoService.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(ProdutoBuilder.getEntity()).get());

        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_URL + "/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(1)));
        Mockito.verify(produtoService, Mockito.times(1)).findById(1);
        Mockito.verifyNoMoreInteractions(produtoService);
    }

    @Test
    public void save() throws Exception {
        Mockito.when(produtoMapper.asEntity(ArgumentMatchers.any())).thenReturn(ProdutoBuilder.getEntity());
        Mockito.when(produtoService.save(ArgumentMatchers.any(Produto.class))).thenReturn(ProdutoBuilder.getEntity());

        mockMvc.perform(
                        MockMvcRequestBuilders.post(ENDPOINT_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(CustomUtils.asJsonString(ProdutoBuilder.getDTO())))
                .andExpect(MockMvcResultMatchers.status().isCreated());
        Mockito.verify(produtoService, Mockito.times(1)).save(ArgumentMatchers.any(Produto.class));
        Mockito.verifyNoMoreInteractions(produtoService);
    }

    @Test
    public void update() throws Exception {
        Mockito.when(produtoMapper.asEntity(ArgumentMatchers.any())).thenReturn(ProdutoBuilder.getEntity());
        Mockito.when(produtoService.update(ArgumentMatchers.any(), ArgumentMatchers.anyInt())).thenReturn(ProdutoBuilder.getEntity());

        mockMvc.perform(
                        MockMvcRequestBuilders.put(ENDPOINT_URL + "/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(CustomUtils.asJsonString(ProdutoBuilder.getDTO())))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(produtoService, Mockito.times(1)).update(ArgumentMatchers.any(Produto.class), ArgumentMatchers.anyInt());
        Mockito.verifyNoMoreInteractions(produtoService);
    }

    @Test
    public void delete() throws Exception {
        Mockito.doNothing().when(produtoService).deleteById(ArgumentMatchers.anyInt());
        mockMvc.perform(
                        MockMvcRequestBuilders.delete(ENDPOINT_URL + "/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        Mockito.verify(produtoService, Mockito.times(1)).deleteById(Mockito.anyInt());
        Mockito.verifyNoMoreInteractions(produtoService);
    }
}