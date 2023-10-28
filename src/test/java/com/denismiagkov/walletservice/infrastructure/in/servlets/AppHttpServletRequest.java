//package com.denismiagkov.walletservice.infrastructure.in.servlets;
//
//import jakarta.servlet.ServletInputStream;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletRequestWrapper;
//
//import java.io.*;
//
//public class AppHttpServletRequest extends HttpServletRequestWrapper {
//
//    private String input;
//
//    public AppHttpServletRequest(HttpServletRequest request, String input) {
//        super(request);
//        this.input = input;
//    }
//
//    /**
//     * Constructs a request object wrapping the given request.
//     *
//     * @param request the {@link HttpServletRequest} to be wrapped.
//     * @throws IllegalArgumentException if the request is null
//     */
//
//
//    @Override
//    public ServletInputStream getInputStream() throws IOException {
//        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(input.getBytes());
//        return AppServletInputStream.getServletInputStream(input);
//
////    @Override
////    public BufferedReader getReader() throws IOException {
////        return new BufferedReader(new InputStreamReader(this.getInputStream()));
////    }
//
//    }
//}