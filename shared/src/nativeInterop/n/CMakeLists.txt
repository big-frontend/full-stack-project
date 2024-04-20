cmake_minimum_required(VERSION 3.23)
project(simple C)

set(CMAKE_C_STANDARD 99)

add_library(api simple.c simple.h)

set_target_properties(simple PROPERTIES PUBLIC_HEADER simple.h)

install(TARGETS simple
        LIBRARY DESTINATION lib
        PUBLIC_HEADER DESTINATION include)