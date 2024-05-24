package com.example.mod_social.comment;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lib_frame.utils.DisplayUtils;
import com.example.mod_social.social.Comment;
import com.example.mod_social.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

public class CommentDialog {

    private Context mContext;
    private ICommentDialogListener mCommentDialogListener;

    private BottomSheetDialog mBottomSheetDialog;
    private CommentInputDialog mCommentInputDialog;
    private final RecyclerView mRcvCommentList;
    private final CommentDialogAdapter mCommentDialogAdapter = new CommentDialogAdapter();
    private final List<Comment> mComments = new ArrayList<>();

    private float slideOffset = 0;

    public CommentDialog(Context context, ICommentDialogListener iCommentDialogListener) {
        mContext = context;
        mCommentDialogListener = iCommentDialogListener;
        mBottomSheetDialog = new BottomSheetDialog(context, R.style.CommentDialog);

        View view = View.inflate(context, R.layout.comment_dialog, null);
        view.findViewById(R.id.dialog_bottomsheet_iv_close).setOnClickListener(view1 -> mBottomSheetDialog.dismiss());
        mRcvCommentList = view.findViewById(R.id.dialog_bottomsheet_rv_lists);
        mCommentDialogAdapter.setItems(mComments);
        mCommentDialogAdapter.setOnItemClickListener((adapter, view12, position) -> initInputTextMsgDialog(adapter.getItem(position), position));
        mRcvCommentList.setAdapter(mCommentDialogAdapter);

        RelativeLayout rl_comment = view.findViewById(R.id.rl_comment);
        rl_comment.setOnClickListener(v -> initInputTextMsgDialog(null, 0));

        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.setCanceledOnTouchOutside(true);
        final BottomSheetBehavior<View> mDialogBehavior = BottomSheetBehavior.from((View) view.getParent());
        mDialogBehavior.setPeekHeight(DisplayUtils.INSTANCE.getScreenHeight());
        mDialogBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    mDialogBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                } else if (newState == BottomSheetBehavior.STATE_SETTLING) {
                    if (slideOffset <= -0.28) {
                        mBottomSheetDialog.dismiss();
                    }
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                CommentDialog.this.slideOffset = slideOffset;
            }
        });
    }

    private void initInputTextMsgDialog(Comment comment, int pos) {
        dismissInputDialog();
        if (mCommentInputDialog == null) {
            mCommentInputDialog = new CommentInputDialog(mContext, R.style.CommentDialog);
        }
        mCommentInputDialog.setmOnTextSendListener(new CommentInputDialog.OnTextSendListener() {
            @Override
            public void onTextSend(String msg) {
                mCommentDialogListener.addComment(mCommentDialogAdapter, comment, msg, pos);
            }

            @Override
            public void dismiss() {
            }
        });
        showInputTextMsgDialog();
    }

    private void showInputTextMsgDialog() {
        mCommentInputDialog.show();
    }

    private void dismissInputDialog() {
        if (mCommentInputDialog != null) {
            if (mCommentInputDialog.isShowing()) {
                mCommentInputDialog.dismiss();
            }
            mCommentInputDialog.cancel();
            mCommentInputDialog = null;
        }
    }

    public void show() {
        mBottomSheetDialog.show();
    }

    public void updateData(List<? extends Comment> comments) {
        mCommentDialogAdapter.setItems(comments);
    }

    public interface ICommentDialogListener {
        void addComment(CommentDialogAdapter adapter, Comment bean, String word, Integer pos);

    }
}
