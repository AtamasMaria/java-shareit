package ru.practicum.shareit.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.request.controller.ItemRequestController;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.service.ItemRequestService;
import ru.practicum.shareit.user.service.UserService;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ItemRequestController.class)
public class ItemRequestControllerTest {
    @Autowired
    ObjectMapper mapper;
    @Autowired
    MockMvc mvc;
    @MockBean
    UserService userService;
    @MockBean
    ItemRequestService requestService;
    private final ItemRequestDto itemRequestDto = ItemRequestDto.builder().id(1L).description("testDescription").build();

    @Test
    void createRequest_whenRequestDtoValid_thenReturnedStatusIsOk() throws Exception {
        Mockito.when(requestService.create(any(), anyLong()))
                .thenReturn(itemRequestDto);

        mvc.perform(post("/requests")
                        .header("X-Sharer-User-Id", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(itemRequestDto)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        verify(requestService).create(itemRequestDto, 1L);
    }

    @Test
    void createRequest_whenRequestDtoNotValid_thenReturnedStatusIsBadRequest() throws Exception {
        ItemRequestDto badItemRequestDto = ItemRequestDto.builder().description("").build();

        Mockito.when(requestService.create(badItemRequestDto, 1L))
                .thenReturn(itemRequestDto);

        mvc.perform(post("/requests")
                        .header("X-Sharer-User-Id", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(badItemRequestDto)))
                .andExpect(status().isBadRequest());
        verify(requestService, never()).create(badItemRequestDto, 1L);
    }

    @Test
    void findById_whenRequestIsExist_thanReturnedStatusIsOk() throws Exception {
        Mockito.when(requestService.findById(anyLong(), anyLong()))
                .thenReturn(itemRequestDto);

        mvc.perform(get("/requests/{requestId}", 1L)
                        .header("X-Sharer-User-Id", 1L))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    void findById_whenRequestIsNotExist_thanReturnedStatusIsNotFound() throws Exception {
        Mockito.when(requestService.findById(anyLong(), anyLong()))
                .thenThrow(new NotFoundException(String.format("Request with id = %d not found.", 1L)));

        mvc.perform(get("/requests/{requestId}", 1L)
                        .header("X-Sharer-User-Id", 1L))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    void findAllUserRequests_whenUserIsExist_thenReturnedStatusIsOk() throws Exception {
        Mockito.when(requestService.findAllUserRequests(anyLong()))
                .thenReturn(Collections.emptyList());

        String result = mvc.perform(get("/requests")
                        .header("X-Sharer-User-Id", 1L))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(result, mapper.writeValueAsString(Collections.emptyList()));
    }

    @Test
    void findAllUserRequests_whenUserIsNotExist_thenReturnedStatusIsNotFound() throws Exception {
        Mockito.when(requestService.findAllUserRequests(anyLong()))
                .thenThrow(new NotFoundException(String.format("User with id = %d not found.", 100L)));

        mvc.perform(get("/requests")
                        .header("X-Sharer-User-Id", 100L))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse()
                .getContentAsString();

        verify(requestService, never()).findAllUserRequests(1L);
    }

    @Test
    void findAllRequestsTest() throws Exception {
        Mockito.when(requestService.findAllRequests(anyLong(), anyInt(), anyInt()))
                .thenReturn(List.of(itemRequestDto));

        mvc.perform(get("/requests/all")
                        .header("X-Sharer-User-Id", 1L))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

}