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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
        when(produtoMapper.asDTOList(any())).thenReturn(ProdutoBuilder.getListDTO());

        when(produtoService.findAll()).thenReturn(ProdutoBuilder.getListEntities());
        mockMvc.perform(get(ENDPOINT_URL))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)));

    }

    @Test
    public void getById() throws Exception {
        when(produtoMapper.asDTO(any())).thenReturn(ProdutoBuilder.getDTO());

        when(produtoService.findById(anyInt())).thenReturn(Optional.of(ProdutoBuilder.getEntity()).get());

        mockMvc.perform(get(ENDPOINT_URL + "/1"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(1)));
        verify(produtoService, times(1)).findById(1);
        verifyNoMoreInteractions(produtoService);
    }

    @Test
    public void save() throws Exception {
        when(produtoMapper.asEntity(any())).thenReturn(ProdutoBuilder.getEntity());
        when(produtoService.save(any(Produto.class))).thenReturn(ProdutoBuilder.getEntity());

        mockMvc.perform(
                        MockMvcRequestBuilders.post(ENDPOINT_URL)
                                .contentType(APPLICATION_JSON)
                                .content(CustomUtils.asJsonString(ProdutoBuilder.getDTO())))
                .andExpect(status().isCreated());
        verify(produtoService, times(1)).save(any(Produto.class));
        verifyNoMoreInteractions(produtoService);
    }

    @Test
    public void update() throws Exception {
        when(produtoMapper.asEntity(any())).thenReturn(ProdutoBuilder.getEntity());
        when(produtoService.update(any(), anyInt())).thenReturn(ProdutoBuilder.getEntity());

        mockMvc.perform(
                        put(ENDPOINT_URL + "/1")
                                .contentType(APPLICATION_JSON)
                                .content(CustomUtils.asJsonString(ProdutoBuilder.getDTO())))
                .andExpect(status().isOk());
        verify(produtoService, times(1)).update(any(Produto.class), anyInt());
        verifyNoMoreInteractions(produtoService);
    }

    @Test
    public void deleteProduct() throws Exception {
        doNothing().when(produtoService).deleteById(anyInt());
        mockMvc.perform(delete(ENDPOINT_URL + "/1"))
                .andExpect(status().isNoContent());
        verify(produtoService, times(1)).deleteById(anyInt());
        verifyNoMoreInteractions(produtoService);
    }
}