create table NTP_PRI_CONF
(
      USR_SID               integer           not null,
      NPR_DSP_GROUP         integer           not null,
      NPR_SORT_KEY1         integer           not null,
      NPR_SORT_ORDER1       integer           not null,
      NPR_SORT_KEY2         integer           not null,
      NPR_SORT_ORDER2       integer           not null,
      NPR_INI_FR_DATE       timestamp         not null,
      NPR_INI_TO_DATE       timestamp         not null,
      NPR_INI_FCOLOR        integer           not null,
      NPR_DSP_LIST          integer           not null,
      NPR_AUTO_RELOAD       integer           not null,
      NPR_DSP_MYGROUP       integer                   ,
      NPR_DSP_POSITION      integer           not null,
      NPR_SMAIL             integer           not null,
      NPR_CMT_SMAIL         integer           not null,
      NPR_GOOD_SMAIL        integer           not null,
      NPR_SCH_KBN           integer           not null,
      NPR_AUID              integer           not null,
      NPR_ADATE             timestamp         not null,
      NPR_EUID              integer           not null,
      NPR_EDATE             timestamp         not null,
      primary key(USR_SID)
);