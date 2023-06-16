package ru.job4j.accidents.controller;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.service.SpringAccidentService;

import java.util.HashSet;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
class IndexControllerTest {
    @MockBean
    private SpringAccidentService service;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    public void shouldReturnDefaultMessage() throws Exception {
        this.mockMvc.perform(get("/index"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("accidents"));
        this.mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("accidents"));
    }

    @Test
    @WithMockUser
    public void shouldReturnModifyPage() throws Exception {
        var accident = new Accident(1, "name", "text", "address",
                new AccidentType(1, "type name"), new HashSet<>());
        Mockito.when(service.findById(1)).thenReturn(Optional.of(accident));

        this.mockMvc.perform(get("/modify/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("modify"))
                .andExpect(model().attributeExists("accident"))
                .andExpect(model().attributeExists("types"))
                .andExpect(model().attributeExists("rules"));

        ArgumentCaptor<Integer> argument = ArgumentCaptor.forClass(Integer.class);
        verify(service).findById(argument.capture());
        assertThat(argument.getValue(), is(1));
    }

    @Test
    @WithMockUser
    public void whenModifyAccident() throws Exception {
        this.mockMvc.perform(post("/modify")
                        .param("id", "1")
                        .param("name", "name test")
                        .param("type.id", "1")
                        .param("rIds", "1")
                        .param("text", "text test")
                        .param("address", "address test"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/index"));

        ArgumentCaptor<Accident> argument = ArgumentCaptor.forClass(Accident.class);
        ArgumentCaptor<String[]> array = ArgumentCaptor.forClass(String[].class);
        verify(service).modify(argument.capture(), array.capture());
        assertThat(argument.getValue().getId(), is(1));
        assertThat(argument.getValue().getName(), is("name test"));
        assertThat(argument.getValue().getText(), is("text test"));
        assertThat(argument.getValue().getAddress(), is("address test"));
        assertThat(array.getValue().length, is(1));
    }

    @Test
    @WithMockUser
    public void shouldReturnSavePage() throws Exception {
        this.mockMvc.perform(get("/save"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("create"))
                .andExpect(model().attributeExists("types"))
                .andExpect(model().attributeExists("rules"));
    }

    @Test
    @WithMockUser
    public void shouldReturnNameUser() throws Exception {
        this.mockMvc.perform(get("/index"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//*[@id=\"navbarCollapse\"]/ul[2]/li[1]/a")
                        .string("user"));
    }

    @Test
    @WithMockUser
    public void whenCreateNewAccident() throws Exception {
        this.mockMvc.perform(post("/save")
                        .param("id", "0")
                        .param("name", "name test")
                        .param("type.id", "1")
                        .param("rIds", "1")
                        .param("text", "text test")
                        .param("address", "address test")
                )
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/index"));

        ArgumentCaptor<Accident> argument = ArgumentCaptor.forClass(Accident.class);
        ArgumentCaptor<String[]> array = ArgumentCaptor.forClass(String[].class);
        verify(service).create(argument.capture(), array.capture());
        assertThat(argument.getValue().getName(), is("name test"));
        assertThat(argument.getValue().getText(), is("text test"));
        assertThat(argument.getValue().getAddress(), is("address test"));
    }

    @Test
    @WithMockUser
    public void whenDeleteAccident() throws Exception {
        this.mockMvc.perform(post("/delete/1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        ArgumentCaptor<Integer> argument = ArgumentCaptor.forClass(Integer.class);
        verify(service).delete(argument.capture());
        assertThat(argument.getValue(), is(1));
    }
}