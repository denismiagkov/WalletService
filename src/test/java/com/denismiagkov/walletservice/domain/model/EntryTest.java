package com.denismiagkov.walletservice.domain.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EntryTest {


    public static final Entry IVAN = new Entry(1, "ivan", "123");
    public static final Entry PETR = new Entry(2, "petr", "321");

    @Test
    void getPlayerId_Should_Return_True() {
        assertThat(IVAN.getPlayerId()).isEqualTo(1);
    }
    @Test
    void getPlayerId_Should_Return_False() {
        assertThat(IVAN.getPlayerId()).isNotEqualTo(2);
    }

    @Test
    void getLogin_Should_Return_True() {
        assertThat(IVAN.getLogin()).isEqualTo("ivan");
    }
    @Test
    void getLogin_Should_Return_False() {
        assertThat(IVAN.getLogin()).isNotEqualTo("petr");
    }

    @Test
    void getPassword_Should_Return_True() {
        assertThat(IVAN.getPassword()).isEqualTo("123");
    }

    @Test
    void getPassword_Should_Return_False() {
        assertThat(IVAN.getPassword()).isNotEqualTo("321");
    }

    @Test
    void testToString() {
    }

    @Test
    void testEquals_Should_Return_False() {
        assertThat(IVAN).isNotEqualTo(PETR);
    }

    @Test
    void testEquals_Should_Return_True() {
        Entry entry =  new Entry(1, "ivan", "123");
        assertThat(IVAN).isEqualTo(entry);
    }

    @Test
    void canEqual() {
    }

    @Test
    void testHashCode_Should_Return_False() {
        assertThat(IVAN).doesNotHaveSameHashCodeAs(PETR);
    }

    @Test
    void testHashCode_Should_Return_True() {
        Entry entry =  new Entry(1, "ivan", "123");
        assertThat(IVAN).hasSameHashCodeAs(entry);
    }
}