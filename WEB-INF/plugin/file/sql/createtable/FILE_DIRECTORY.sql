 create table FILE_DIRECTORY
   (
     FDR_SID          integer      not null,
     FDR_VERSION      integer      not null,
     FCB_SID          integer      not null,
     FDR_PARENT_SID   integer      not null,
     FDR_KBN          integer      not null,
     FDR_VER_KBN      integer      not null,
     FDR_LEVEL        integer      not null,
     FDR_NAME         varchar(255) not null,
     FDR_BIKO         varchar(3000),
     FDR_JTKBN        integer      not null,
     FDR_AUID         integer      not null,
     FDR_ADATE        timestamp    not null,
     FDR_EUID         integer      not null,
     FDR_EDATE        timestamp    not null,
     FDR_EGID         integer      not null,
     FDR_ACCESS_SID integer not null,
     FDR_TRADE_DATE   timestamp,
     FDR_TRADE_TARGET varchar(50),
     FDR_TRADE_MONEYKBN integer,
     FDR_TRADE_MONEY  decimal(16, 4),
     EMT_SID          integer,
     primary key (FDR_SID, FDR_VERSION)
   );
