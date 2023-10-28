//package com.denismiagkov.walletservice.infrastructure.in.servlets;
//
//import jakarta.servlet.ReadListener;
//import jakarta.servlet.ServletInputStream;
//
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//
//public class AppServletInputStream {
//    String input;
//    private String myString;
//
//
//    public static ServletInputStream getServletInputStream(String myString) throws UnsupportedEncodingException {
//        byte[] myBytes = myString.getBytes("UTF-8");
//        ServletInputStream servletInputStream = new ServletInputStream() {
//            private int lastIndexRetrieved = -1;
//            private ReadListener readListener = null;
//
//            @Override
//            public boolean isFinished() {
//                return (lastIndexRetrieved == myBytes.length - 1);
//            }
//
//            @Override
//            public boolean isReady() {
//                // This implementation will never block
//                // We also never need to call the readListener from this method, as this method will never return false
//                return isFinished();
//            }
//
//            @Override
//            public void setReadListener(ReadListener readListener) {
//                this.readListener = readListener;
//                if (!isFinished()) {
//                    try {
//                        readListener.onDataAvailable();
//                    } catch (IOException e) {
//                        readListener.onError(e);
//                    }
//                } else {
//                    try {
//                        readListener.onAllDataRead();
//                    } catch (IOException e) {
//                        readListener.onError(e);
//                    }
//                }
//            }
//
//            @Override
//            public int read() throws IOException {
//                int i;
//                if (!isFinished()) {
//                    i = myBytes[lastIndexRetrieved + 1];
//                    lastIndexRetrieved++;
//                    if (isFinished() && (readListener != null)) {
//                        try {
//                            readListener.onAllDataRead();
//                        } catch (IOException ex) {
//                            readListener.onError(ex);
//                            throw ex;
//                        }
//                    }
//                    return i;
//                } else {
//                    return -1;
//                }
//            }
//        };
//        return servletInputStream;
//    }
//
//    public static void main(String[] args) throws UnsupportedEncodingException {
//        String s = "123";
//        ServletInputStream servletInputStream = getServletInputStream(s);
//        System.out.println(servletInputStream);
//    }
//
//}
