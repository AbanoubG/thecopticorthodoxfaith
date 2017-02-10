package org.copticchurch.library.discoverorthodoxy.activities.createuser;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;

import org.copticchurch.library.discoverorthodoxy.activities.BrainPhaserActivity;
import org.copticchurch.library.discoverorthodoxy.logic.UserManager;
import org.copticchurch.library.discoverorthodoxy.model.User;

import javax.inject.Inject;

import org.copticchurch.library.discoverorthodoxy.BrainPhaserComponent;
import org.copticchurch.library.discoverorthodoxy.BuildConfig;
import org.copticchurch.library.discoverorthodoxy.activities.main.MainActivity;
import org.copticchurch.library.discoverorthodoxy.database.UserDataSource;

/**
 * Created by funkv on 15.02.2016.
 * <p/>
 * Activity used to create an user. Queries Username and avatar. <p/> Persistent data: none
 * Parameters: Intent with AC
 */
public class CreateUserActivity extends BrainPhaserActivity implements TextView.OnEditorActionListener, AvatarPickerDialogFragment.AvatarPickerDialogListener {
    public static final String KEY_USER_ID = "user_id";
    @Inject
    UserManager mUserManager;
    @Inject
    UserDataSource mUserDataSource;
    private TextView mUsernameInput;
    private TextInputLayout mUsernameInputLayout;
    private User mEditingUser = null;
    private FloatingActionButton mloadAvatarPickerDialog;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void injectComponent(BrainPhaserComponent component) {
        component.inject(this);
    }

    /**
     * Instantiates the view
     *
     * @param savedInstanceState Ignored
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(org.copticchurch.library.discoverorthodoxy.R.layout.activity_create_user);

        mloadAvatarPickerDialog = (FloatingActionButton) findViewById(org.copticchurch.library.discoverorthodoxy.R.id.loadAvatarPickerDialog);
        mloadAvatarPickerDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateUsernameDuplicate() && validateUsernameLength()) {
                    createAvatarSelectDialog();
                }
            }
        });
        mUsernameInput = (TextView) findViewById(org.copticchurch.library.discoverorthodoxy.R.id.input_username);
        mUsernameInputLayout = (TextInputLayout) findViewById(org.copticchurch.library.discoverorthodoxy.R.id.input_username_layout);
        mUsernameInput.setOnEditorActionListener(this);

        // Watch for changes and trigger validation
        mUsernameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                validateUsernameDuplicate();
                validateUsernameLength();
            }
        });

        ImageView avatar = (ImageView) findViewById(org.copticchurch.library.discoverorthodoxy.R.id.avatar);
        if (getIntent().getAction().equals(Intent.ACTION_EDIT)) {
            // Read user to edit
            long userId = getIntent().getLongExtra(KEY_USER_ID, -1L);
            User user = mUserDataSource.getById(userId);
            if (BuildConfig.DEBUG && user == null) {
                throw new AssertionError();
            }
            mEditingUser = user;

            // Pre-fill view
            avatar.setImageResource(Avatars.getAvatarResourceId(getApplicationContext(), user.getAvatar()));
            mUsernameInput.setText(user.getName());
        } else {
            // Set default avatar
            avatar.setImageResource(Avatars.getDefaultAvatarResourceId());
        }

    }

    /*
     * Validate username and update GUI with errors
     */
    private boolean validateUsernameLength() {
        boolean isValid;
        String username = mUsernameInput.getText().toString().trim();
        if (username.length() == 0) {
            mUsernameInput.setError(getString(org.copticchurch.library.discoverorthodoxy.R.string.empty_username));
            mUsernameInputLayout.setErrorEnabled(true);
            isValid = false;
        } else if (username.length() > UserDataSource.MAX_USERNAME_LENGTH) {
            mUsernameInput.setError(getString(org.copticchurch.library.discoverorthodoxy.R.string.too_long_username));
            mUsernameInputLayout.setErrorEnabled(true);
            isValid = false;
        } else {
            mUsernameInputLayout.setErrorEnabled(false);
            isValid = true;
        }
        return isValid;
    }

    /**
     * Validate duplicates and update GUI with errors.
     */
    private boolean validateUsernameDuplicate() {
        boolean isValid;

        String username = mUsernameInput.getText().toString();
        User foundUser = mUserDataSource.findOneByName(username);
        if (foundUser != null) {
            // In edit mode do not check against currently editing user
            if (getIntent().getAction().equals(Intent.ACTION_EDIT)
                    && foundUser.getId() == mEditingUser.getId()) {
                isValid = true;
            } else {
                mUsernameInput.setError(getString(org.copticchurch.library.discoverorthodoxy.R.string.taken_username));
                mUsernameInputLayout.setErrorEnabled(true);
                isValid = false;
            }
        } else {
            mUsernameInputLayout.setErrorEnabled(false);
            isValid = true;
        }
        return isValid;
    }

    /*
     * Called when an action is performed on the username input. Opens avatar selection after
     * username confirmation.
     */
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE) { // Enter is pressed
            if (validateUsernameDuplicate() && validateUsernameLength()) {
                createAvatarSelectDialog();
            }
        }
        return false;
    }

    /**
     * Loads the avatar selection dialog
     */
    private void createAvatarSelectDialog() {
        FragmentManager fm = getSupportFragmentManager();
        AvatarPickerDialogFragment avatarPickerDialog = new AvatarPickerDialogFragment();
        avatarPickerDialog.show(fm, "avatar_picker");
    }

    /*
     * Called when an avatar has been selected in the selection dialog.
     */
    @Override
    public void onAvatarSelected(AvatarPickerDialogFragment dialog, String avatarResourceName) {
        TextView input = (TextView) findViewById(org.copticchurch.library.discoverorthodoxy.R.id.input_username);
        profileCreationFinished(input.getText().toString(), avatarResourceName);
    }

    /*
     * Called when avatar selection has been cancelled
     */
    @Override
    public void onCancelled(AvatarPickerDialogFragment dialog) {
        TextView input = (TextView) findViewById(org.copticchurch.library.discoverorthodoxy.R.id.input_username);
        profileCreationFinished(input.getText().toString(), Avatars.getDefaultAvatarResourceName());
    }

    /**
     * Called when the profile creation has been finished. Depending on the intent the activity was
     * called with, the user is created or updated.
     *
     * @param username           Username that was entered
     * @param avatarResourceName Resource name of the user's selected avatar
     */
    private void profileCreationFinished(String username, String avatarResourceName) {
        // Update avatar for smooth transition via shared element
        ImageView avatar = (ImageView) findViewById(org.copticchurch.library.discoverorthodoxy.R.id.avatar);
        avatar.setImageResource(Avatars.getAvatarResourceId(getApplicationContext(), avatarResourceName));

        if (getIntent().getAction().equals(Intent.ACTION_INSERT)) {
            // Create user
            User user = new User();
            user.setAvatar(avatarResourceName);
            user.setName(username);
            mUserDataSource.create(user);

            // Login user and change to category selection
            mUserManager.switchUser(user);

            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        } else if (getIntent().getAction().equals(Intent.ACTION_EDIT)) {
            if (BuildConfig.DEBUG && mEditingUser == null) {
                throw new AssertionError();
            }

            mEditingUser.setAvatar(avatarResourceName);
            mEditingUser.setName(username);
            mUserDataSource.update(mEditingUser);
        }

        setResult(RESULT_OK);
        finish();
    }
}
