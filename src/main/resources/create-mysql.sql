create table if not exists area_codes
(
    area_code int primary key,
    area_name varchar(20) not null
);

create table if not exists sigungu_codes
(
    area_code    int         not null,
    sigungu_code int         not null,
    sigungu_name varchar(50) not null,
    primary key (area_code, sigungu_code),
    foreign key (area_code) references area_codes (area_code)
);

create table if not exists content_types
(
    content_type_id   int primary key,
    content_type_name varchar(50) not null
);

create table if not exists category_codes
(
    category_code varchar(9) primary key,
    category_name varchar(50) not null
);

create table if not exists area_based_contents
(
    addr1         varchar(255)  null,
    addr2         varchar(255)  null,
    areacode      int           null,
    booktour      int           null,
    cat1          varchar(3)    null,
    cat2          varchar(5)    null,
    cat3          varchar(9)    null,
    contentid     int           not null,
    contenttypeid int           not null,
    createdtime   datetime      not null,
    firstimage    varchar(2048) null,
    firstimage2   varchar(2048) null,
    cpyrht_div_cd varchar(10)   null,
    mapx          double        null,
    mapy          double        null,
    mlevel        int           null,
    modifiedtime  datetime      not null,
    sigungucode   int           null,
    tel           varchar(255)  null,
    title         varchar(255)  not null,
    zipcode       varchar(10)   null,
    primary key (contentid),
    foreign key (areacode) references area_codes(area_code),
    foreign key (contenttypeid) references content_types(content_type_id),
    foreign key (cat1) references category_codes(category_code),
    foreign key (cat2) references category_codes(category_code),
    foreign key (cat3) references category_codes(category_code)
);


create table if not exists detail_commons
(
    contentId int primary key,
    foreign key (contentId) references area_based_contents(contentId)
);