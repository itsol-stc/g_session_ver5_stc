create table RNG_TEMPLATE_KEIRO
(
  RTK_SID           integer     not null,
  RCT_SID           integer     not null,
  USR_SID           integer     not null,
  RTP_SID           integer     not null,
  RTP_VER           integer     not null,
  RTK_SORT          integer     not null,
  RTK_LEVEL         integer     not null,
  RTK_NAME          varchar(10) not null,
  RTK_TYPE          integer     not null,
  RTK_ROLL_TYPE     integer     not null,
  RTK_OUTCONDITION  integer     not null,
  RTK_OUTCOND_BORDER integer    not null,
  RTK_NOUSER        integer     not null,
  RTK_ADDSTEP       integer     not null,
  RTK_KEIRO_SKIP    integer     not null,
  RTK_KEIRO_KOETU   integer     not null,
  RTK_MULTISEL_FLG  integer     not null,
  RTK_BOSSSTEP_CNT  integer     not null,
  RTK_BOSSSTEP_MASTCNT integer  not null,
  RTK_AUID          integer     not null,
  RTK_ADATE         timestamp   not null,
  RTK_EUID          integer     not null,
  RTK_EDATE         timestamp   not null,
  RTK_JKBN          integer     not null,
  RTK_KOETU_SIZI    integer     not null,
  RTK_OWNSINGI      integer     not null,
  RTK_KEIRO_COMMENT      varchar(200),
      primary key    (RTK_SID)
);