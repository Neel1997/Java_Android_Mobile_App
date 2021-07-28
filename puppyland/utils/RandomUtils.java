package com.plusultra.puppyland.utils;

import com.plusultra.puppyland.types.ObstacleType;

import java.util.Random;

class RandomUtils {

    public static ObstacleType getRandomObstacleType() {
        RandomEnum<ObstacleType> randomEnum = new RandomEnum<ObstacleType>(ObstacleType.class);
        return randomEnum.random();
    }

    private static class RandomEnum<E extends Enum> {

        private static final Random RND = new Random();
        private final E[] values;

        RandomEnum(Class<E> token) {
            values = token.getEnumConstants();
        }

        E random() {
            return values[RND.nextInt(values.length)];
        }
    }

}
