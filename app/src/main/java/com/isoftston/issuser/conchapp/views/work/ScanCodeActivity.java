package com.isoftston.issuser.conchapp.views.work;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.corelibs.base.BaseActivity;
import com.corelibs.utils.PreferencesHelper;
import com.corelibs.utils.ToastMgr;
import com.corelibs.utils.download.BGADownloadProgressEvent;
import com.corelibs.utils.download.BGAUpgradeUtil;
import com.google.zxing.client.android.CaptureActivity;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.constants.Constant;
import com.isoftston.issuser.conchapp.constants.Urls;
import com.isoftston.issuser.conchapp.model.bean.DangerTypeBean;
import com.isoftston.issuser.conchapp.model.bean.FileBean;
import com.isoftston.issuser.conchapp.model.bean.FixWorkBean;
import com.isoftston.issuser.conchapp.model.bean.ImageInfoBean;
import com.isoftston.issuser.conchapp.model.bean.ResponseDataBean;
import com.isoftston.issuser.conchapp.model.bean.SubmitJobBody;
import com.isoftston.issuser.conchapp.model.bean.UploadWxzyspdRequestBean;
import com.isoftston.issuser.conchapp.model.bean.UserInfoBean;
import com.isoftston.issuser.conchapp.model.bean.WorkBean;
import com.isoftston.issuser.conchapp.model.bean.WorkDetailBean;
import com.isoftston.issuser.conchapp.model.bean.WorkDetailRequestBean;
import com.isoftston.issuser.conchapp.presenter.WorkDetailPresenter;
import com.isoftston.issuser.conchapp.utils.DateUtils;
import com.isoftston.issuser.conchapp.utils.ToastUtils;
import com.isoftston.issuser.conchapp.views.interfaces.WorkDetailView;
import com.isoftston.issuser.conchapp.views.mine.adapter.ScanInfoAdapter;
import com.isoftston.issuser.conchapp.views.security.ChoicePhotoActivity;
import com.isoftston.issuser.conchapp.views.work.adpter.WorkAnnexAdapter;
import com.isoftston.issuser.conchapp.weight.DownloadingDialog;
import com.isoftston.issuser.conchapp.weight.NavBar;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Subscriber;
import rx.functions.Action1;


public class ScanCodeActivity extends BaseActivity<WorkDetailView, WorkDetailPresenter> implements WorkDetailView, View.OnClickListener {
    private static final String TAG = "ScanCodeActivity";
    private static final int REQUEST_CAMERA_PERMISSION_CODE = 100;
    private static final int REQUEST_STORAGE_PERMISSION_CODE = 101;
    private static final int OPEN_ACTIVITY_SCAN_CODE = 102;
    private static final int OPEN_ACTIVITY_TAKE_PHOTO_CODE = 103;
    private static final int MODIFY_CODE = 104;
    private static final int CHOICE_PHOTO_CODE = 1110;
    @Bind(R.id.nav)
    NavBar nav;
    @Bind(R.id.iv_add)
    ImageView refreshIv;//刷新按钮
    @Bind(R.id.revoke_btn)
    Button revokeBtn;
    @Bind(R.id.commit_btn)
    Button commitBtn;
    @Bind(R.id.modify_btn)
    Button modifyBtn;
    @Bind(R.id.ll_gas_point)
    LinearLayout ll_gas_point;
    @Bind(R.id.ll_gas_check)
    LinearLayout ll_gas_check;
    @Bind(R.id.project_name_tv)
    TextView projectNameTv;//项目名称
    @Bind(R.id.project_name_time_day)
    TextView projectTimeDayTv;//项目时间(天)
    @Bind(R.id.work_start_time_day)
    TextView work_start_time_day;//作业开始时间
    @Bind(R.id.work_end_time_day)
    TextView work_end_time_day;//作业结束时间

    //四个标识圆点及相关人员
    @Bind(R.id.charge_person_iv)
    ImageView chargePersonIv;//负责人
    @Bind(R.id.charge_person)
    TextView chargePersonTv;
    @Bind(R.id.charge_person_relname)
    TextView chargePersonRelnameTv;

    @Bind(R.id.guardian_iv)
    ImageView guardianPersonIv;//监护人
    @Bind(R.id.guardian)
    TextView guardianTv;
    @Bind(R.id.guardian_relname)
    TextView guardianRelnameTv;

    @Bind(R.id.auditor_iv)
    ImageView auditorPersonIv;//审核人
    @Bind(R.id.auditor)
    TextView auditorTv;
    @Bind(R.id.auditor_relname)
    TextView auditorRelnameTv;
    @Bind(R.id.gas_person_iv)
    ImageView gasPersonIv;//检测人
    @Bind(R.id.gas_person)
    TextView gasTv;
    @Bind(R.id.gas_person_relname)
    TextView gasRelnameTv;
    @Bind(R.id.approver_iv)
    ImageView approverPersonIv;//审核人
    @Bind(R.id.approver)
    TextView approverTv;
    @Bind(R.id.approver_relname)
    TextView approverRelnameTv;
    //底部列表显示具体人名
    @Bind(R.id.person_in_charge_tv)
    TextView personInChargeNmaeTv;//负责人
    //    @Bind(R.id.lead_rl)
//    RelativeLayout leadRl;//负责人布局
    @Bind(R.id.guardian_tv)
    TextView guardianNameTv;//监护人
    @Bind(R.id.auditor_tv)
    TextView auditorNameTv;//审核人
    @Bind(R.id.approver_tv)
    TextView approverNameTv;//批准人
    //其他信息
    @Bind(R.id.equipment_type_tv)
    TextView equipmentTypeTv;//设备类型
    @Bind(R.id.equipment_model_tv)
    TextView equipmentModelTv;//设备型号
    @Bind(R.id.equipment_name_tv)
    TextView equipmentNameTv;//设备名称
    @Bind(R.id.work_zone_tv)
    TextView workZoneTv;//作业区域
    @Bind(R.id.work_address_tv)
    TextView workAddressTv;//作业地点
    @Bind(R.id.work_content_tv)
    TextView workContentTv;//作业内容
    @Bind(R.id.work_company_tv)
    TextView workCompanyTv;//作业单位
    @Bind(R.id.work_number_tv)
    TextView workNumberTv;//作业人数
    @Bind(R.id.danger_work_type_tv)
    TextView dangerWorkTypeTv;//危险作业类型
    @Bind(R.id.gas_checker_tv)
    TextView gasCheckerTv;//气体检测人
    @Bind(R.id.gas_rl)
    RelativeLayout gasRl;
    @Bind(R.id.danger_work_rl)
    RelativeLayout dangerWorkRl;//危险作业
    @Bind(R.id.scan_success_layout)
    LinearLayout scanSuccessHintLayout;//扫码成功，点击可继续扫码或拍照布局
    @Bind(R.id.scan_or_photo_tv)
    TextView scanOrPhotoSuccessTv;//扫码/拍照成功

    @Bind(R.id.scan_layout_outter)//没有扫过二维码显示的布局
            LinearLayout scanCodeLl;
    LinearLayout scanCodeLayout;////扫码
    LinearLayout takePhotoLayout;//拍照
    //    @Bind(R.id.scan_flag_iv)
    ImageView scanFlagIv1;//第一轮扫码、拍照标记view
    //    @Bind(R.id.photo_flag_iv)
    ImageView photoFlagIv1;
    private boolean isScaned1 = false;//是否扫过
    private boolean isPhotoed1 = false;//是否拍过照
    @Bind(R.id.add_form)
    RelativeLayout addForm;

    @Bind(R.id.scaned_layout)
    LinearLayout scanedLayout;
    @Bind(R.id.scan_layout_inner)//扫过二维码后显示的布局
            LinearLayout scanCodeLlInner;
    LinearLayout scanCodeInner;//扫码
    View scanFlagIv;
    LinearLayout takePhotoInnerLayout;//拍照
    View photoFlagIv;
    //    @Bind(R.id.scan_flag_iv1)
    ImageView scanFlagIv2;//第二轮扫码、拍照标记view
    //    @Bind(R.id.photo_flag_iv1)
    ImageView photoFlagIv2;

    @Bind(R.id.scan_info_lv)
    ListView mListView;
    @Bind(R.id.form_recycler_view)
    RecyclerView formRecyclerView;

    private List<ImageInfoBean> datas = new ArrayList<>();
    private ScanInfoAdapter mAdapter;
    private boolean isScaned2 = false;
    private boolean isPhotoed2 = false;
    private boolean isGasPerson = false;//是否为负责人
    private boolean isChargePerson = false;//是否为负责人
    private boolean isGurdianPerson = false;//是否为监护人
    private boolean isAuditorPerson = false;//是否为审核人
    private boolean isApproverPerson = false;//是否为批准人
    private boolean isChargePersonScaned = false;//负责人是否扫过
    private boolean isChargePersonPhotoed = false;//负责人是拍过
    private boolean isOneTurnDone = false;//第一轮是否完成

    private boolean isCommited = false;
    private boolean isDangerWork;
    private String jobId = "";
    private String userId = "";
    private boolean isGasPersonDown = false;//检测人是否提交
    private boolean isChargePersonDown = false;//负责人是否提交
    private boolean isNoneJob = false;//有没有职位（不在四个职位之内）
    private static final int SCANED1 = 1;
    private static final int PHOTED1 = 2;
    private static final int SCANED2 = 3;
    private static final int PHOTED2 = 4;
    private boolean flage1 = false;
    private boolean flage2 = false;
    private boolean flage3 = false;
    private boolean flage0 = false;
    private List<WorkBean> workBeanList = new ArrayList<>();

    private HashMap<String, String> picMap = new HashMap<>();
    private List<FileBean> formList;
    private WorkAnnexAdapter adapter;

    @SuppressLint("HandlerLeak")
    public Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SCANED1:
                    isScaned1 = true;
                    hintScan1Success();
                    break;
                case PHOTED1:
                    isPhotoed2 = true;
                    hintPhoto1Success();
                    break;
                case SCANED2:
                    isScaned2 = true;
                    hintScan2Success();
                    break;
                case PHOTED2:
                    isPhotoed2 = true;
                    hintPhoto2Success();
                    break;
                case 5://当前用户提交后改变ui
                    isCommited = true;
                    hideAllBtn();
                    scanCodeLl.setVisibility(View.GONE);
                    scanedLayout.setVisibility(View.GONE);
                    if (turn == 2) {
                        scanCodeLayout.setVisibility(View.VISIBLE);
                        scanCodeLlInner.setVisibility(View.GONE);
                        mListView.setVisibility(View.VISIBLE);
                    }
                    if (turn == 1) {
                        if (isGasPerson) {
                            changeGasToGreen();
                        } else if (isChargePerson) {
                            changeChargersToGreen();
                        } else if (isGurdianPerson) {
                            changeGuardiansToGreen();
                        } else if (isAuditorPerson) {
                            changeAuditorsToGreen();
                        } else if (isApproverPerson) {
                            changeApproversToGreen();
                        }
                    } else {
                        if (isGasPerson) {
                            changeGasToBlue();
                        } else if (isChargePerson) {
                            changeChargersToBlue();
                        } else if (isGurdianPerson) {
                            changeGuardiansToBlue();
                        } else if (isAuditorPerson) {
                            changeAuditorsToBlue();
                        } else if (isApproverPerson) {
                            changeApproversToBlue();
                        }
                    }
                    break;
                default:
                    break;
            }
        }

    };

    private WorkDetailBean workDetailBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_scan_code;
    }

    public static Intent getLauncher(Context context, String jobId, boolean b) {
        Intent intent = new Intent(context, ScanCodeActivity.class);
        intent.putExtra("jobId", jobId);
        intent.putExtra("isDangerWork", b);
        return intent;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initView();
        nav.setColorRes(R.color.app_blue);
        nav.setNavTitle(getString(R.string.work_point_information));
        refreshIv.setVisibility(View.VISIBLE);
        refreshIv.setImageResource(R.mipmap.refresh);//改为刷新
        getLoadingDialog().show();
        presenter.getUserInfo();
        presenter.getWorkInfo();
//        scan();
//        setData();
//        scaned();

        // 监听下载进度
        BGAUpgradeUtil.getDownloadProgressEventObservable()
                .compose(this.<BGADownloadProgressEvent>bindToLifecycle())
                .subscribe(new Action1<BGADownloadProgressEvent>() {
                    @Override
                    public void call(BGADownloadProgressEvent downloadProgressEvent) {
                        if (mDownloadingDialog != null && mDownloadingDialog.isShowing() && downloadProgressEvent.isNotDownloadFinished() && downloadProgressEvent.getProgress() >= 0 && downloadProgressEvent.getTotal() >= 0) {
                            mDownloadingDialog.setProgress(downloadProgressEvent.getProgress(), downloadProgressEvent.getTotal());
                        }
                    }
                });
    }

    /**
     * 测试数据
     */
    private void setData() {
        mAdapter = new ScanInfoAdapter(this, datas, workDetailBean);
        mListView.setAdapter(mAdapter);
        setListViewHeightBasedOnChildren(mListView);
    }

    /**
     * 先获取用户信息,然后判断其职位
     * 检查当前用户的职位
     * 扫描和拍照后改变ui
     */
    int status;

    private int turn = 1;
    int gasCount = 0;
    int guardianCount = 0;
    int auditorCount = 0;
    int approverCount = 0;
    int leadingCount = 0;

    private void checkUserPosition(WorkDetailBean bean) {
        hideAllBtn();
        status = bean.status;
        switch (status) {
            case 0://新建：
                if (bean.gas == null) {
                    if (userId.equals(bean.leading)) {
                        showAllBtn();
                    } else if (userId.equals(bean.approver) || bean.equals(bean.auditor)
                            || userId.equals(bean.guardian)) {
                        commitBtn.setVisibility(View.GONE);
                        scanCodeLl.setVisibility(View.GONE);
                    } else {
                        scanCodeLl.setVisibility(View.GONE);
                    }
                } else {
                    if (userId.equals(bean.gas)) {
                        commitBtn.setVisibility(View.VISIBLE);
                        if (datas != null && datas.size() > 0) {
                            for (ImageInfoBean imageInfoBean : datas) {
                                if (imageInfoBean.getUserId().equals(bean.gas)) {
                                    commitBtn.setVisibility(View.GONE);
                                    scanCodeLl.setVisibility(View.GONE);
                                    changeGasToGreen();
                                }
                            }
                        }
                    } else if (userId.equals(bean.leading)) {
                        if (datas != null && datas.size() == 0) {
                            revokeBtn.setVisibility(View.VISIBLE);
                            modifyBtn.setVisibility(View.VISIBLE);
                            scanCodeLl.setVisibility(View.GONE);
                        } else if (datas != null && datas.size() > 0) {
                            changeGasToGreen();
                            for (ImageInfoBean imageInfoBean : datas) {
                                if (imageInfoBean.getUserId().equals(bean.gas)) {
                                    commitBtn.setVisibility(View.VISIBLE);
                                    scanCodeLl.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    } else if (userId.equals(bean.approver) || bean.equals(bean.auditor)
                            || userId.equals(bean.guardian)) {
                        if (datas != null && datas.size() > 0) {
                            for (ImageInfoBean imageInfoBean : datas) {
                                if (imageInfoBean.getUserId().equals(bean.gas)) {
                                    changeGasToGreen();
                                }
                            }
                        }
                        commitBtn.setVisibility(View.GONE);
                        scanCodeLl.setVisibility(View.GONE);
                    } else {
                        if (datas != null && datas.size() > 0) {
                            for (ImageInfoBean imageInfoBean : datas) {
                                if (imageInfoBean.getUserId().equals(bean.gas)) {
                                    changeGasToGreen();
                                }
                            }
                        }
                        scanCodeLl.setVisibility(View.GONE);
                    }
                }

                break;
            case 1://撤销：仅显示UI
                hideAllBtn();
                scanCodeLl.setVisibility(View.GONE);
                break;
            case 2://负责人开工扫描：第一轮负责人已提交了
                //改变负责人相关UI
                if (bean.gas == null) {
                    changeChargersToGreen();
                    if (userId.equals(bean.leading)) {
                        hideAllBtn();
                        scanCodeLl.setVisibility(View.GONE);
                    }
                    isChargePersonDown = true;
                    flage0 = true;
                    for (ImageInfoBean imageInfoBean : datas) {
                        if (imageInfoBean.getUserId().equals(bean.guardian)) {
                            changeGuardiansToGreen();
                            flage1 = true;
                        } else if (imageInfoBean.getUserId().equals(bean.auditor)) {
                            changeAuditorsToGreen();
                            flage2 = true;
                        } else if (imageInfoBean.getUserId().equals(bean.approver)) {
                            changeApproversToGreen();
                            flage3 = true;
                        }
                    }
                } else {
                    changeGasToGreen();
                    if (userId.equals(bean.gas) || userId.equals(bean.leading)) {
                        hideAllBtn();
                        scanCodeLl.setVisibility(View.GONE);
                    }
                    isGasPersonDown = true;
                    for (ImageInfoBean imageInfoBean : datas) {
                        if (imageInfoBean.getUserId().equals(bean.guardian)) {
                            changeGuardiansToGreen();
                            flage1 = true;
                        } else if (imageInfoBean.getUserId().equals(bean.auditor)) {
                            changeAuditorsToGreen();
                            flage2 = true;
                        } else if (imageInfoBean.getUserId().equals(bean.approver)) {
                            changeApproversToGreen();
                            flage3 = true;
                        } else if (imageInfoBean.getUserId().equals(bean.leading)) {
                            changeChargersToGreen();
                            flage0 = true;
                        }
                    }
                }

                break;
            case 3://第一轮完成
                turn = 2;
                changeScanLayout();
                flage1 = true;
                flage2 = true;
                flage3 = true;
                flage0 = true;
                isOneTurnDone = true;
                if (bean.gas == null) {
                    if (!userId.equals(bean.leading)) {
                        scanedLayout.setVisibility(View.VISIBLE);
                        scanCodeLlInner.setVisibility(View.GONE);
                        commitBtn.setVisibility(View.GONE);
                    } else {
                        commitBtn.setVisibility(View.VISIBLE);
                        scanedLayout.setVisibility(View.VISIBLE);
                    }
                } else {
                    scanedLayout.setVisibility(View.VISIBLE);
                    gasCount = 0;
                    if (userId.equals(bean.leading)) {
                        for (ImageInfoBean imageInfoBean : datas) {
                            if (imageInfoBean.getUserId().equals(bean.gas)) {
                                gasCount = gasCount + 1;
                            }
                        }
                        if (gasCount == 2) {
                            changeGasToBlue();
                            commitBtn.setVisibility(View.VISIBLE);
                            scanCodeLlInner.setVisibility(View.VISIBLE);
                        } else {
                            changeGasToGreen();
                            commitBtn.setVisibility(View.GONE);
                            scanCodeLlInner.setVisibility(View.GONE);

                        }

                    } else if (userId.equals(bean.gas)) {
                        for (ImageInfoBean imageInfoBean : datas) {
                            if (imageInfoBean.getUserId().equals(bean.gas)) {
                                gasCount = gasCount + 1;
                            }
                        }
                        if (gasCount == 1) {
                            changeGasToGreen();
                            commitBtn.setVisibility(View.VISIBLE);
                            scanCodeLlInner.setVisibility(View.VISIBLE);
                        } else if (gasCount == 2) {
                            changeGasToBlue();
                            commitBtn.setVisibility(View.GONE);
                            scanCodeLlInner.setVisibility(View.GONE);

                        }
                    } else {
                        for (ImageInfoBean imageInfoBean : datas) {
                            if (imageInfoBean.getUserId().equals(bean.gas)) {
                                gasCount = gasCount + 1;
                            }
                        }
                        if (gasCount == 2) {
                            changeGasToBlue();
                        } else {
                            changeGasToGreen();
                        }
                        scanCodeLlInner.setVisibility(View.GONE);
                        commitBtn.setVisibility(View.GONE);

                    }

                }
                break;
            case 4://负责人结束扫描:第二轮负责人已提交
                isOneTurnDone = true;
                guardianCount = 0;
                auditorCount = 0;
                approverCount = 0;
                leadingCount = 0;
                turn = 2;
                if (bean.gas == null) {
                    changeChargersToBlue();
                    changeGuardiansToGreen();
                    changeAuditorsToGreen();
                    changeApproversToGreen();
                    isChargePersonDown = true;
                    //1.根据历史信息改变相关人员的UI， 2.根据当前用户展示不同按钮
                    for (ImageInfoBean imageInfoBean : datas) {
                        if (imageInfoBean.getUserId().equals(bean.guardian)) {
                            guardianCount = guardianCount + 1;
                        }
                        if (imageInfoBean.getUserId().equals(bean.auditor)) {
                            auditorCount = auditorCount + 1;
                        }
                        if (imageInfoBean.getUserId().equals(bean.approver)) {
                            approverCount = approverCount + 1;
                        }
                    }
                } else {
                    changeGasToBlue();
                    changeChargersToGreen();
                    changeGuardiansToGreen();
                    changeAuditorsToGreen();
                    changeApproversToGreen();
                    isGasPersonDown = true;
                    for (ImageInfoBean imageInfoBean : datas) {
                        if (imageInfoBean.getUserId().equals(bean.guardian)) {
                            guardianCount = guardianCount + 1;
                        }
                        if (imageInfoBean.getUserId().equals(bean.auditor)) {
                            auditorCount = auditorCount + 1;
                        }
                        if (imageInfoBean.getUserId().equals(bean.approver)) {
                            approverCount = approverCount + 1;
                        }
                        if (imageInfoBean.getUserId().equals(bean.leading)) {
                            leadingCount = leadingCount + 1;
                        }
                    }
                }

                scanCodeInner.setBackgroundResource(R.drawable.scaned_code_btn_shape);
                takePhotoInnerLayout.setBackgroundResource(R.drawable.scaned_code_btn_shape);
                break;
            case 5://完成:两轮都完成
                //隐藏所以功能按钮
                changeGasToBlue();
                changeChargersToBlue();
                changeGuardiansToBlue();
                changeAuditorsToBlue();
                changeApproversToBlue();
                scanCodeLl.setVisibility(View.GONE);
                scanedLayout.setVisibility(View.GONE);
                turn = 2;
                flage0 = true;
                flage1 = true;
                flage2 = true;
                flage3 = true;
                approverCount = 2;
                auditorCount = 2;
                guardianCount = 2;
                leadingCount = 2;
                break;
            default:
                break;
        }
        if (bean.gas != null && status == 3) {
            scan();
            scaned();
            return;
        }
        if (turn == 2) {
            scanedLayout.setVisibility(View.VISIBLE);
            scanCodeLl.setVisibility(View.GONE);
            scanCodeLlInner.setVisibility(View.GONE);
            commitBtn.setVisibility(View.GONE);
        }
        if (leadingCount == 1 && userId.equals(bean.getLeading())) {
            commitBtn.setVisibility(View.VISIBLE);
            scanCodeLlInner.setVisibility(View.VISIBLE);
        } else if (leadingCount == 2) {
            changeChargersToBlue();
        }
        if (guardianCount == 1 && userId.equals(bean.getGuardian())) {
            commitBtn.setVisibility(View.VISIBLE);
            scanCodeLlInner.setVisibility(View.VISIBLE);
        } else if (guardianCount == 2) {
            changeGuardiansToBlue();
        }
        if (auditorCount == 1 && userId.equals(bean.getAuditor())) {
            commitBtn.setVisibility(View.VISIBLE);
            scanCodeLlInner.setVisibility(View.VISIBLE);
        } else if (auditorCount == 2) {
            changeAuditorsToBlue();
        }
        if (approverCount == 1 && userId.equals(bean.getApprover())) {
            commitBtn.setVisibility(View.VISIBLE);
            scanCodeLlInner.setVisibility(View.VISIBLE);
        } else if (approverCount == 2) {
            changeApproversToBlue();
        }
        //比对自己的职位
        if (userId.equals(bean.gas)) {
            isGasPerson = true;
        } else if (userId.equals(bean.leading)) {
            isChargePerson = true;
            if (!flage0 && bean.status > 1) {
                commitBtn.setVisibility(View.VISIBLE);
            }
            if (leadingCount == 2) {
                commitBtn.setVisibility(View.GONE);
                scanCodeLlInner.setVisibility(View.GONE);
            }
        } else if (userId.equals(bean.guardian)) {
            isGurdianPerson = true;
            if (!flage1 && bean.status > 1) {
                commitBtn.setVisibility(View.VISIBLE);
            } else {
                scanCodeLl.setVisibility(View.GONE);
            }
            if (guardianCount == 2) {
                commitBtn.setVisibility(View.GONE);
                scanCodeLlInner.setVisibility(View.GONE);
            }
        } else if (userId.equals(bean.auditor)) {
            isAuditorPerson = true;
            if (!flage2 && bean.status > 1) {
                commitBtn.setVisibility(View.VISIBLE);
            } else {
                scanCodeLl.setVisibility(View.GONE);
            }
            if (auditorCount == 2) {
                commitBtn.setVisibility(View.GONE);
                scanCodeLlInner.setVisibility(View.GONE);
            }

        } else if (userId.equals(bean.approver)) {
            isApproverPerson = true;
            if (!flage3 && bean.status > 1) {
                commitBtn.setVisibility(View.VISIBLE);
            } else {
                scanCodeLl.setVisibility(View.GONE);
            }
            if (approverCount == 2) {
                commitBtn.setVisibility(View.GONE);
                scanCodeLlInner.setVisibility(View.GONE);
            }

        } else {
            isNoneJob = true;
        }
        if (isNoneJob) {
            hideAllBtn();
            scanCodeLl.setVisibility(View.GONE);
            scanedLayout.setVisibility(View.VISIBLE);
            scanCodeLlInner.setVisibility(View.GONE);
        }
        Log.i("test", "---test:" + approverCount + "---" + auditorCount + "---" + guardianCount);

        if (turn == 2 && bean.status == 3) {
            if (bean.gas == null) {
                if (userId.equals(bean.leading)) {
                    commitBtn.setVisibility(View.VISIBLE);
                    scanCodeLlInner.setVisibility(View.VISIBLE);
                    scanedLayout.setVisibility(View.VISIBLE);
                }
            } else {
                if (userId.equals(bean.gas)) {
                    commitBtn.setVisibility(View.VISIBLE);
                    scanCodeLlInner.setVisibility(View.VISIBLE);
                    scanedLayout.setVisibility(View.VISIBLE);
                }
            }


        }
        //第一轮完成后才显示列表数据
        if (turn == 2) {
            scanedLayout.setVisibility(View.VISIBLE);
            mListView.setVisibility(View.VISIBLE);
        } else {
            scanedLayout.setVisibility(View.VISIBLE);
            scanCodeLlInner.setVisibility(View.GONE);
            mListView.setVisibility(View.VISIBLE);
        }
        scan();
        scaned();

    }

    private void changeGasToGreen() {
        gasPersonIv.setImageResource(R.drawable.dots_green);
        gasTv.setTextColor(getResources().getColor(R.color.colorGreen));
        gasRelnameTv.setTextColor(getResources().getColor(R.color.colorGreen));
        gasCheckerTv.setTextColor(getResources().getColor(R.color.colorGreen));
    }

    private void changeGasToBlue() {
        gasPersonIv.setImageResource(R.drawable.dots_blue);
        gasTv.setTextColor(getResources().getColor(R.color.colorDarkBlue));
        gasRelnameTv.setTextColor(getResources().getColor(R.color.colorDarkBlue));
        gasCheckerTv.setTextColor(getResources().getColor(R.color.colorDarkBlue));
    }

    private void changeGuardiansToBlue() {
        guardianPersonIv.setImageResource(R.drawable.dots_blue);
        guardianTv.setTextColor(getResources().getColor(R.color.colorDarkBlue));
        guardianRelnameTv.setTextColor(getResources().getColor(R.color.colorDarkBlue));
        guardianNameTv.setTextColor(getResources().getColor(R.color.colorDarkBlue));
    }

    private void changeAuditorsToBlue() {
        auditorPersonIv.setImageResource(R.drawable.dots_blue);
        auditorTv.setTextColor(getResources().getColor(R.color.colorDarkBlue));
        auditorRelnameTv.setTextColor(getResources().getColor(R.color.colorDarkBlue));
        auditorNameTv.setTextColor(getResources().getColor(R.color.colorDarkBlue));
    }

    private void changeApproversToBlue() {
        approverPersonIv.setImageResource(R.drawable.dots_blue);
        approverTv.setTextColor(getResources().getColor(R.color.colorDarkBlue));
        approverRelnameTv.setTextColor(getResources().getColor(R.color.colorDarkBlue));
        approverNameTv.setTextColor(getResources().getColor(R.color.colorDarkBlue));
    }

    private void changeChargersToBlue() {
        chargePersonIv.setImageResource(R.drawable.dots_blue);
        chargePersonTv.setTextColor(getResources().getColor(R.color.colorDarkBlue));
        chargePersonRelnameTv.setTextColor(getResources().getColor(R.color.colorDarkBlue));
        personInChargeNmaeTv.setTextColor(getResources().getColor(R.color.colorDarkBlue));
    }

    private void changeApproversToGreen() {
        approverPersonIv.setImageResource(R.drawable.dots_green);
        approverTv.setTextColor(getResources().getColor(R.color.colorGreen));
        approverRelnameTv.setTextColor(getResources().getColor(R.color.colorGreen));
        approverNameTv.setTextColor(getResources().getColor(R.color.colorGreen));
    }

    private void changeAuditorsToGreen() {
        auditorPersonIv.setImageResource(R.drawable.dots_green);
        auditorTv.setTextColor(getResources().getColor(R.color.colorGreen));
        auditorRelnameTv.setTextColor(getResources().getColor(R.color.colorGreen));
        auditorNameTv.setTextColor(getResources().getColor(R.color.colorGreen));
    }

    private void hintPhoto1Success() {
        scanSuccessHintLayout.setVisibility(View.VISIBLE);
        scanOrPhotoSuccessTv.setText(R.string.photo_action);
        photoFlagIv1.setVisibility(View.VISIBLE);//显示已拍照图标
    }

    private void hintScan1Success() {
        scanSuccessHintLayout.setVisibility(View.VISIBLE);
        scanOrPhotoSuccessTv.setText(R.string.scan_code);
        scanFlagIv1.setVisibility(View.VISIBLE);
    }

    private void changeGuardiansToGreen() {
        guardianPersonIv.setImageResource(R.drawable.dots_green);//圆点变色
        guardianTv.setTextColor(getResources().getColor(R.color.colorGreen));//圆点文字变色
        guardianRelnameTv.setTextColor(getResources().getColor(R.color.colorGreen));//具体人名变色
        guardianNameTv.setTextColor(getResources().getColor(R.color.colorGreen));//人名变色
    }

    private void changeChargersToGreen() {
        chargePersonIv.setImageResource(R.drawable.dots_green);
        chargePersonTv.setTextColor(getResources().getColor(R.color.colorGreen));
        chargePersonRelnameTv.setTextColor(getResources().getColor(R.color.colorGreen));
        personInChargeNmaeTv.setTextColor(getResources().getColor(R.color.colorGreen));
    }

    private void hintPhoto2Success() {
        scanSuccessHintLayout.setVisibility(View.VISIBLE);
        scanOrPhotoSuccessTv.setText(R.string.photo_action);
        photoFlagIv2.setVisibility(View.VISIBLE);
    }

    private void hintScan2Success() {
        scanSuccessHintLayout.setVisibility(View.VISIBLE);
        scanOrPhotoSuccessTv.setText(R.string.scan_code);
        scanFlagIv2.setVisibility(View.VISIBLE);
    }

    private void showOldUi() {
        chargePersonIv.setImageResource(R.drawable.dots_green);
        chargePersonTv.setTextColor(getResources().getColor(R.color.colorGreen));
        chargePersonRelnameTv.setTextColor(getResources().getColor(R.color.colorGreen));
        personInChargeNmaeTv.setTextColor(getResources().getColor(R.color.colorGreen));

        guardianPersonIv.setImageResource(R.drawable.dots_green);
        guardianTv.setTextColor(getResources().getColor(R.color.colorGreen));
        guardianRelnameTv.setTextColor(getResources().getColor(R.color.colorGreen));
        guardianNameTv.setTextColor(getResources().getColor(R.color.colorGreen));

        auditorPersonIv.setImageResource(R.drawable.dots_green);
        auditorTv.setTextColor(getResources().getColor(R.color.colorGreen));
        auditorRelnameTv.setTextColor(getResources().getColor(R.color.colorGreen));
        auditorNameTv.setTextColor(getResources().getColor(R.color.colorGreen));

        approverPersonIv.setImageResource(R.drawable.dots_green);
        approverTv.setTextColor(getResources().getColor(R.color.colorGreen));
        approverRelnameTv.setTextColor(getResources().getColor(R.color.colorGreen));
        approverNameTv.setTextColor(getResources().getColor(R.color.colorGreen));
    }

    private void showOrHideCommitBtn() {
        if (isCommited) {
            hideAllBtn();
        } else {
            commitBtn.setVisibility(View.VISIBLE);
        }
    }

    private void initView() {

        //没有扫描和拍照，显示布局
        scanCodeLayout = scanCodeLl.findViewById(R.id.scan_code_layout);
//        scanFlagIv = scanCodeLayout.findViewById(R.id.scan_flag_iv1);
        takePhotoLayout = scanCodeLl.findViewById(R.id.take_photo_layout);
//        photoFlagIv = takePhotoLayout.findViewById(R.id.photo_flag_iv1);
        scanFlagIv1 = scanCodeLl.findViewById(R.id.scan_flag_iv1);
        photoFlagIv1 = scanCodeLl.findViewById(R.id.photo_flag_iv1);
        //所有人扫过后按钮在内部显示
        scanCodeInner = scanCodeLlInner.findViewById(R.id.scan_code_layout);
        takePhotoInnerLayout = scanCodeLlInner.findViewById(R.id.take_photo_layout);
        scanFlagIv2 = scanCodeLlInner.findViewById(R.id.scan_flag_iv1);
        photoFlagIv2 = scanCodeLlInner.findViewById(R.id.photo_flag_iv1);

        jobId = getIntent().getStringExtra("jobId");
        isDangerWork = getIntent().getBooleanExtra("isDangerWork", false);
        Log.e(TAG, "----jobId:" + jobId + "----" + isDangerWork);
        clicks();
    }

    private void clicks() {
        revokeBtn.setOnClickListener(this);
        commitBtn.setOnClickListener(this);
    }

    @OnClick(R.id.modify_btn)
    public void modify() {//修改
        Intent intent = new Intent(this, FixWorkActivity.class);
        intent.putExtra("id", jobId);
        Bundle bundle = new Bundle();
        bundle.putSerializable("fixbean", workDetailBean);
        intent.putExtras(bundle);
        startActivityForResult(intent, MODIFY_CODE);
    }

    /**
     * 刷新数据
     */
    @OnClick(R.id.iv_add)
    public void refresh() {
        presenter.getWorkDetailInfo(jobId);
    }

    private void scaned() {//扫过二维码
        scanCodeInner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission(0);
            }
        });
        takePhotoInnerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(ChoicePhotoActivity.getLauncher(ScanCodeActivity.this, "0", map, 1), OPEN_ACTIVITY_TAKE_PHOTO_CODE);
            }
        });
    }

    private HashMap<String, String> map = new HashMap<>();

    private void scan() {//没有扫过二维码
        scanCodeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission(0);
            }
        });
        takePhotoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(ChoicePhotoActivity.getLauncher(ScanCodeActivity.this, "0", map, 1), OPEN_ACTIVITY_TAKE_PHOTO_CODE);
            }
        });
    }

    private void checkPermission(int type) {
        if (type == 0) {
            if (ContextCompat.checkSelfPermission(getViewContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                //申请摄像头权限
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION_CODE);
            } else {
                startScanCode();
            }
        }
    }

    /**
     * 申请摄像头权限返回结果
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CAMERA_PERMISSION_CODE://摄像头权限
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //获得授权,开始扫描
                    startScanCode();
                } else {
                    ToastMgr.show(getString(R.string.check_manager_open_pemission));
                }
                return;
            case REQUEST_STORAGE_PERMISSION_CODE://摄像头权限
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //获得授权,开始扫描
                    downloadFile(fileName,filePath);
                } else {
                    ToastMgr.show(getString(R.string.check_manager_open_pemission));
                }
                return;
            default:
                break;
        }
    }

    private void startScanCode() {
        //开始扫描
        Intent intent = new Intent(getViewContext(), CaptureActivity.class);
        startActivityForResult(intent, OPEN_ACTIVITY_SCAN_CODE);
    }

    /**
     * 扫描二维码返回结果
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == OPEN_ACTIVITY_SCAN_CODE && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String s = bundle.getString("result");
            Log.e(TAG, "---扫描结果：" + s);
            if (s.equals(getString(R.string.check_manager_cancel_scan))) {
                //取消扫码
                ToastMgr.show(getString(R.string.check_manager_cancel_scan));
            } else {
                if (TextUtils.equals(s, equipmentModelTv.getText().toString())) {
                    showResultView(s);
                } else {
                    ToastUtils.showtoast(ScanCodeActivity.this, "您扫描的二维码有误，请扫描正确的二维码！");
                }

            }
        } else if (requestCode == OPEN_ACTIVITY_TAKE_PHOTO_CODE && resultCode == 10) {
            map = (HashMap<String, String>) data.getSerializableExtra(Constant.TEMP_PIC_LIST);
            Log.e(TAG, "----size:" + map.size() + ",--" + map.toString());
//            if (status == 4) {
//                handler.sendEmptyMessage(PHOTED2);
//            } else if (status == 2) {
            Message message = new Message();

            if (turn == 1) {
                message.what = PHOTED1;
            } else {
                message.what = PHOTED2;
            }
            handler.sendMessage(message);
//            }
        } else if (requestCode == MODIFY_CODE) {
            presenter.getWorkDetailInfo(jobId);
        } else if (requestCode == CHOICE_PHOTO_CODE) {
            if (resultCode == 10) {
                picMap = (HashMap<String, String>) data.getSerializableExtra(Constant.TEMP_PIC_LIST);
                StringBuilder builder = new StringBuilder();
                for (String path : picMap.values()) {
                    builder.append(path);
                    builder.append(",");
                }
                String picString = builder.toString();

                UploadWxzyspdRequestBean param = new UploadWxzyspdRequestBean();
                param.setId(workDetailBean.getId());
                param.setZyspdList(picString.substring(0,picString.length()-1));
                presenter.uploadWxzyspd(param);
            }
        }
    }

    /**
     * 提示扫码成功
     */
    private void showResultView(final String result) {
        final Dialog dialog = new Dialog(this, R.style.Dialog);
        dialog.show();
        LayoutInflater inflater = LayoutInflater.from(this);
        final View viewDialog = inflater.inflate(R.layout.scan_success_layout, null);
        Display display = this.getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();
        //设置dialog的宽高为屏幕的宽高
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(width, height);
        dialog.setContentView(viewDialog, layoutParams);
        TextView equipmentNumber = viewDialog.findViewById(R.id.equipment_name_number_tv);
        Button sureBtn = viewDialog.findViewById(R.id.scan_success_sure_btn);
        //获取扫码得到的编号
        equipmentNumber.setText(result);
        sureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
//                if (status == 4) {
//                    handler.sendEmptyMessage(SCANED2);
//                } else if (status == 2) {
                Message message = new Message();
//                message.what=SCANED1;
                if (turn == 1) {
                    message.what = SCANED1;
                } else {
                    message.what = SCANED2;
                }
                handler.sendMessage(message);
//                }
            }
        });

    }

    /**
     * 所有人扫过后显示布局
     */
    private void changeScanLayout() {
        scanCodeLl.setVisibility(View.GONE);
        scanedLayout.setVisibility(View.GONE);
        scanCodeInner.setBackgroundResource(R.drawable.scaned_code_btn_shape);
        takePhotoInnerLayout.setBackgroundResource(R.drawable.scaned_code_btn_shape);
        showOldUi();
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    @Override
    protected WorkDetailPresenter createPresenter() {
        return new WorkDetailPresenter();
    }

    @Override
    public void renderData(WorkDetailBean workDetailBean) {
    }


    @Override
    public void getWorkDetailInfo(WorkDetailRequestBean workBean) {
        //填充数据
        workDetailBean = workBean.work;
        //查看提交过的信息、图片
        datas.clear();
        if (workBean.list.size() > 0) {
            datas = workBean.list;
            setData();
        }
//        work_start_time_day
        if (workDetailBean.status == 1 || workDetailBean.status == 5) {
            hideAllBtn();
        }
        if (workDetailBean.getGasName() == null) {
            ll_gas_point.setVisibility(View.GONE);
            ll_gas_check.setVisibility(View.GONE);
        } else {
            ll_gas_point.setVisibility(View.VISIBLE);
            ll_gas_check.setVisibility(View.VISIBLE);
        }
        projectNameTv.setText(workDetailBean.name);
        String createTime = DateUtils.format_yyyy_MM_dd_china.format(workDetailBean.createTime);
        projectTimeDayTv.setText(createTime);
        String startTime = DateUtils.format_yyyy_MM_dd_HH_mm.format(workDetailBean.startTime);
        work_start_time_day.setText(startTime);
        if (!(workDetailBean.endTime == 0)) {
            String endTime = DateUtils.format_yyyy_MM_dd_HH_mm.format(workDetailBean.endTime);
            work_end_time_day.setText(endTime);
        }

        String gasName = workDetailBean.gasName;
        if (gasName != null) {
            gasRl.setVisibility(View.VISIBLE);
            gasCheckerTv.setText(gasName);
            gasRelnameTv.setText(gasName);
        } else {
            gasRl.setVisibility(View.GONE);
        }
        String chargeName = workDetailBean.leadingName;
        if (chargeName != null) {
            chargePersonRelnameTv.setText(chargeName);
            personInChargeNmaeTv.setText(chargeName);
        }
        String guardianName = workDetailBean.guardianName;
        if (guardianName != null) {
            guardianRelnameTv.setText(guardianName);
            guardianNameTv.setText(guardianName);
        }
        String auditorName = workDetailBean.auditorName;
        if (auditorName != null) {
            auditorRelnameTv.setText(auditorName);
            auditorNameTv.setText(auditorName);
        }
        String approverName = workDetailBean.approverName;
        if (approverName != null) {
            approverRelnameTv.setText(approverName);
            approverNameTv.setText(approverName);
        }
        equipmentTypeTv.setText(workDetailBean.equipmentTypeName);
        equipmentModelTv.setText(workDetailBean.equipmentCode);
        equipmentNameTv.setText(workDetailBean.equipmentName);
        for (WorkBean workBean1 : workBeanList) {
            if (workBean1.getId() == Integer.parseInt(workDetailBean.area)) {
                workZoneTv.setText(workBean1.getName());
            }
        }
        workAddressTv.setText(workDetailBean.part);
        workContentTv.setText(workDetailBean.content);
        workCompanyTv.setText(workDetailBean.companyName);
        workNumberTv.setText(String.valueOf(workDetailBean.numberPeople));
        if (workDetailBean.isDanger == 1) {
            isDangerWork = true;
        } else {
            isDangerWork = false;
        }
        if (isDangerWork) {
            gasCheckerTv.setText(workDetailBean.gasName);
            dangerWorkRl.setVisibility(View.VISIBLE);
            dangerWorkTypeTv.setText(workDetailBean.typeName);
        } else {
            dangerWorkRl.setVisibility(View.GONE);
        }
        //如果状态为完成且自己是负责人 显示上传按钮
        if (workDetailBean.getStatus() == 5 && userId.equals(workDetailBean.getLeading()) && ("".equals(workDetailBean.getZyspdList()) || workDetailBean.getZyspdList() == null)) {
            addForm.setVisibility(View.VISIBLE);
        } else {
            addForm.setVisibility(View.GONE);
        }

        checkUserPosition(workDetailBean);
        getLoadingDialog().dismiss();
        if (workDetailBean.getZyspdList() != null && !"".equals(workDetailBean.getZyspdList())) {
            addForm.setVisibility(View.GONE);
            formList = new ArrayList<>();
            String[] fileArray = workDetailBean.getZyspdList().split(",");
            for (String fileBeanArray : fileArray) {
                FileBean fileBean = new FileBean();
                fileBean.setFileName(fileBeanArray.split(":")[0]);
                fileBean.setFilePath(fileBeanArray.split(":")[1]);
                formList.add(fileBean);
            }
            LinearLayoutManager layoutmanager = new LinearLayoutManager(this);
            layoutmanager.setOrientation(LinearLayoutManager.HORIZONTAL);
            //设置RecyclerView 布局
            formRecyclerView.setLayoutManager(layoutmanager);
            //设置Adapter
            adapter = new WorkAnnexAdapter(formList);
            formRecyclerView.setAdapter(adapter);
            adapter.setOnItemClickListener(new WorkAnnexAdapter.OnItemClickListener() {
                @Override
                public void onClick(int position) {
                    fileName = formList.get(position).getFileName();
                    String path = formList.get(position).getFilePath();
                    if (!path.startsWith("uploadDir")){
                        filePath = Urls.ROOT+path;
                    }else {
                        filePath = Urls.IMAGE_ROOT+path;
                    }
                    if (isWifiConnected(getViewContext())){
                        if (ContextCompat.checkSelfPermission(getViewContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(ScanCodeActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_STORAGE_PERMISSION_CODE);
                        }else {
                            downloadFile(filePath,fileName);
                        }
                    }else{
                        showDialogView(filePath,fileName);
                    }
                }
            });
        }
    }

    private String fileName;
    private String filePath;

    private void hideAllBtn() {
        revokeBtn.setVisibility(View.GONE);
        commitBtn.setVisibility(View.GONE);
        modifyBtn.setVisibility(View.GONE);
    }

    private void showAllBtn() {
        revokeBtn.setVisibility(View.VISIBLE);
        commitBtn.setVisibility(View.VISIBLE);
        modifyBtn.setVisibility(View.VISIBLE);
    }

    @Override
    public void responseError(int type) {
        if (type == 0) {
            ToastMgr.show(R.string.internet_error);
            getLoadingDialog().dismiss();
            Log.e(TAG, "----获取作业详细信息失败");
        } else if (type == 1) {
            ToastMgr.show(R.string.back_failed);
            getLoadingDialog().dismiss();
            Log.e(TAG, "----撤销作业失败");
        } else if (type == 2) {
            ToastMgr.show(R.string.subimt_failed);
            getLoadingDialog().dismiss();
            Log.e(TAG, "----提交作业失败");
        } else if (type == 3) {
            ToastMgr.show(R.string.uploadWx_failed);
            getLoadingDialog().dismiss();
            Log.e(TAG, "----危险作业审批单信息上传失败");
        }
    }

    @Override
    public void revokeJob(ResponseDataBean responseDataBean) {
        ToastMgr.show(R.string.revoke_success);
        hideAllBtn();
        scanCodeLl.setVisibility(View.GONE);
    }

    @Override
    public void submitJob(ResponseDataBean responseDataBean) {
        ToastMgr.show(R.string.submit_success);
        handler.sendEmptyMessage(5);
        hideAllBtn();
        presenter.getWorkDetailInfo(jobId);
        scanSuccessHintLayout.setVisibility(View.GONE);
        if (status > 2) {
            scanedLayout.setVisibility(View.VISIBLE);
            scanCodeLlInner.setVisibility(View.GONE);
        } else {
            scanCodeLl.setVisibility(View.GONE);
        }
        if (turn == 2) {
            Log.e(TAG, "----turn==2");
            scanedLayout.setVisibility(View.VISIBLE);
            scanCodeLlInner.setVisibility(View.GONE);
        }
    }

    @Override
    public void uploadWxzyspdSuccess() {
        ToastMgr.show(R.string.uploadWx_success);
        presenter.getWorkDetailInfo(jobId);
    }

    @Override
    public void getUserInfo(UserInfoBean userInfoBean) {
        userId = userInfoBean.getId();
    }

    @Override
    public void getWorkError() {

    }

    @Override
    public void getWorkListInfo(List<WorkBean> list) {
        workBeanList = list;
        //获取作业详情
        presenter.getWorkDetailInfo(jobId);
    }


    @Override
    public void onLoadingCompleted() {
        hideLoading();
    }

    @Override
    public void onAllPageLoaded() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.revoke_btn:
                presenter.revokeJob(jobId);
                break;
            case R.id.commit_btn:
                final boolean scaned = isScaned1 || isScaned2;
                final boolean photoed = isPhotoed1 || isPhotoed2;
                if (scaned && photoed && map != null && map.size() > 0) {
                    SubmitJobBody body = new SubmitJobBody();
                    body.code = equipmentModelTv.getText().toString();
                    body.workId = jobId;
                    body.location = PreferencesHelper.getData(Constant.LOCATION_NAME);
                    StringBuilder stringBuilder = new StringBuilder();
                    for (String path : map.values()) {
                        stringBuilder.append(path).append(',');
                    }
                    body.imgs = stringBuilder.toString();
                    presenter.submitJob(body);
                } else {
                    ToastUtils.showtoast(this, "扫描和拍照同时完成以后才可以提交！");
                }
                break;
        }
    }


    /**
     * 下载 文件
     */
    public void downloadFile(String url, final String fileName) {
            // 如果文件已经下载过了，直接 打开
            if (BGAUpgradeUtil.isFileDownloaded(fileName)) {
                getViewContext().startActivity(BGAUpgradeUtil.openFile(fileName));
                return;
            }
            // 下载文件
            BGAUpgradeUtil.downloadApkFile(url, fileName)
                    .subscribe(new Subscriber<File>() {
                        @Override
                        public void onStart() {
                            showDownloadingDialog();
                        }

                        @Override
                        public void onCompleted() {
                            dismissDownloadingDialog();
                        }

                        @Override
                        public void onError(Throwable e) {
                            ToastMgr.show(getString(R.string.download_fail));
                            BGAUpgradeUtil.deleteFile(fileName);
                            dismissDownloadingDialog();
                        }

                        @Override
                        public void onNext(File file) {
                            if (file != null) {
                                getViewContext().startActivity(BGAUpgradeUtil.openFile(file));
                            }
                        }
                    });
    }

    private void showDialogView(final String url, final String fileName) {
        AlertDialog alert = new AlertDialog.Builder(getViewContext()).setTitle(R.string.tip_wifi_state)
                .setMessage(R.string.action_cancel)
                .setPositiveButton(R.string.login_confirm, new DialogInterface.OnClickListener() {
                    @Override//处理确定按钮点击事件
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();//对话框关闭。
                        if (ContextCompat.checkSelfPermission(getViewContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(ScanCodeActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_STORAGE_PERMISSION_CODE);
                        }else {
                            downloadFile(filePath,fileName);
                        }
                    }
                })
                .setNegativeButton(R.string.wait_again, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();//对话框关闭。
                    }
                })
                .setCancelable(true)
                .create();
        alert.show();
    }

    public boolean isWifiConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWiFiNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (mWiFiNetworkInfo != null) {
                return mWiFiNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    private DownloadingDialog mDownloadingDialog;

    /**
     * 显示下载对话框
     */
    private void showDownloadingDialog() {
        if (mDownloadingDialog == null) {
            mDownloadingDialog = new DownloadingDialog(this);
        }
        mDownloadingDialog.show();
    }

    /**
     * 隐藏下载对话框
     */
    private void dismissDownloadingDialog() {
        if (mDownloadingDialog != null) {
            mDownloadingDialog.dismiss();
        }
    }
    @OnClick(R.id.add_form)
    public void addForm(){
        startActivityForResult(ChoicePhotoActivity.getLauncher(ScanCodeActivity.this,"0",picMap,0),CHOICE_PHOTO_CODE);
    }
}
