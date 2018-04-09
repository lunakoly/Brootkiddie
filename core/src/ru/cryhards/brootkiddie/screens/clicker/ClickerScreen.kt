package ru.cryhards.brootkiddie.screens.clicker

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import ru.cryhards.brootkiddie.Core
import ru.cryhards.brootkiddie.ui.UI
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar.ProgressBarStyle
import ru.cryhards.brootkiddie.items.Scripts
import java.util.*

import java.nio.file.Files.size

import ru.cryhards.brootkiddie.Environment


/**
 * Created by user on 4/9/18.
 */
class ClickerScreen : ScreenAdapter() {

    val stage = Stage()


    private val uiGroup = Table()
    private val backButton = UI.GlitchImageButton("img/ui/back.png")
    private val nameLabel = UI.StaticLabel("Press to set name")
    private val progressBar : ProgressBar
    private val clickerField = UI.StaticLabel("Click here")
    private val newScriptButton = UI.StaticLabel("New Script")

    private var progress = 0.0f
    private var step = 0.01f
    private var script = Scripts.emptyItem()

    init {

        uiGroup.setFillParent(true)
        uiGroup.debug = true
        stage.addActor(uiGroup)



        backButton.setPosition(50f, 50f, Align.bottomLeft)
        backButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                Core.instance.toGlobalMap()
            }
        })

        newScriptButton.setPosition(Gdx.graphics.width - 50f, 50f, Align.bottomRight)

        clickerField.addListener(object : ClickListener(){
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                progress+=step
                updateBar()
                Gdx.app.log("Clicker", progress.toString())
            }
        })


        newScriptButton.addListener(object : ClickListener(){
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                newScript()
                super.clicked(event, x, y)
            }
        })

        progressBar = createProgressBar()

        clickerField.setWrap(true)

        uiGroup.add(progressBar).fillX().row()
        uiGroup.add(clickerField).expand().fill()


        stage.addActor(backButton)
        stage.addActor(newScriptButton
        )

    }


    private fun createProgressBar(): ProgressBar {
        var pixmap = Pixmap(100, 20, Pixmap.Format.RGBA8888)
        pixmap.setColor(Color.RED)
        pixmap.fill()

        var drawable = TextureRegionDrawable(TextureRegion(Texture(pixmap)))
        pixmap.dispose()


        val progressBarStyle = ProgressBarStyle()
        progressBarStyle.background = drawable

        pixmap = Pixmap(0, 20, Pixmap.Format.RGBA8888)
        pixmap.setColor(Color.GREEN)
        pixmap.fill()
        drawable = TextureRegionDrawable(TextureRegion(Texture(pixmap)))
        pixmap.dispose()

        progressBarStyle.knob = drawable

        pixmap = Pixmap(100, 20, Pixmap.Format.RGBA8888)
        pixmap.setColor(Color.GREEN)
        pixmap.fill()
        drawable = TextureRegionDrawable(TextureRegion(Texture(pixmap)))
        pixmap.dispose()

        progressBarStyle.knobBefore = drawable

        val progressBar = ProgressBar(0.0f, 1.0f, 0.01f, false, progressBarStyle)
        progressBar.value = 0f
        progressBar.setAnimateDuration(0.05f)
        progressBar.setBounds(10f, 10f, 100f, 20f)

        return progressBar
    }

    fun updateBar() {
        progressBar.value = progress
        clickerField.setText(getText().subSequence(0, (getText().length*progress).toInt()))
        if (progress >= 1.0f) finishScript()
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(45/255f, 45/255f, 45/255f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        stage.act()
        stage.draw()
    }


    override fun show() {
        super.show()
        Gdx.input.inputProcessor = stage

    }

    private fun getText() : String{
        return "\n" +
                "\n" +
                "\n" +
                "\n" +
                "struct group_info init_groups = { .usage = ATOMIC_INIT(2) };\n" +
                "\n" +
                "struct group_info *groups_alloc(int gidsetsize){\n" +
                "\n" +
                "    struct group_info *group_info;\n" +
                "\n" +
                "    int nblocks;\n" +
                "\n" +
                "    int i;\n" +
                "\n" +
                "\n" +
                "\n" +
                "    nblocks = (gidsetsize + NGROUPS_PER_BLOCK - 1) / NGROUPS_PER_BLOCK;\n" +
                "\n" +
                "    /* Make sure we always allocate at least one indirect block pointer */\n" +
                "\n" +
                "    nblocks = nblocks ? : 1;\n" +
                "\n" +
                "    group_info = kmalloc(sizeof(*group_info) + nblocks*sizeof(gid_t *), GFP_USER);\n" +
                "\n" +
                "    if (!group_info)\n" +
                "\n" +
                "        return NULL;\n" +
                "\n" +
                "    group_info->ngroups = gidsetsize;\n" +
                "\n" +
                "    group_info->nblocks = nblocks;\n" +
                "\n" +
                "    atomic_set(&group_info->usage, 1);\n" +
                "\n" +
                "\n" +
                "\n" +
                "    if (gidsetsize <= NGROUPS_SMALL)\n" +
                "\n" +
                "        group_info->blocks[0] = group_info->small_block;\n" +
                "\n" +
                "    else {\n" +
                "\n" +
                "        for (i = 0; i < nblocks; i++) {\n" +
                "\n" +
                "            gid_t *b;\n" +
                "\n" +
                "            b = (void *)__get_free_page(GFP_USER);\n" +
                "\n" +
                "            if (!b)\n" +
                "\n" +
                "                goto out_undo_partial_alloc;\n" +
                "\n" +
                "            group_info->blocks[i] = b;\n" +
                "\n" +
                "        }\n" +
                "\n" +
                "    }\n" +
                "\n" +
                "    return group_info;\n" +
                "\n" +
                "\n" +
                "\n" +
                "out_undo_partial_alloc:\n" +
                "\n" +
                "    while (--i >= 0) {\n" +
                "\n" +
                "        free_page((unsigned long)group_info->blocks[i]);\n" +
                "\n" +
                "    }\n" +
                "\n" +
                "    kfree(group_info);\n" +
                "\n" +
                "    return NULL;\n" +
                "\n" +
                "}\n" +
                "\n" +
                "\n" +
                "\n" +
                "EXPORT_SYMBOL(groups_alloc);\n" +
                "\n" +
                "\n" +
                "\n" +
                "void groups_free(struct group_info *group_info)\n" +
                "\n" +
                "{\n" +
                "\n" +
                "    if (group_info->blocks[0] != group_info->small_block) {\n" +
                "\n" +
                "        int i;\n" +
                "\n" +
                "        for (i = 0; i < group_info->nblocks; i++)\n" +
                "\n" +
                "            free_page((unsigned long)group_info->blocks[i]);\n" +
                "\n" +
                "    }\n" +
                "\n" +
                "    kfree(group_info);\n" +
                "\n" +
                "}\n" +
                "\n" +
                "\n" +
                "\n" +
                "EXPORT_SYMBOL(groups_free);\n" +
                "\n" +
                "\n" +
                "\n" +
                "/* export the group_info to a user-space array */\n" +
                "\n" +
                "static int groups_to_user(gid_t __user *grouplist,\n" +
                "\n" +
                "              const struct group_info *group_info)\n" +
                "\n" +
                "{\n" +
                "\n" +
                "    int i;\n" +
                "\n" +
                "    unsigned int count = group_info->ngroups;\n" +
                "\n" +
                "\n" +
                "\n" +
                "    for (i = 0; i < group_info->nblocks; i++) {\n" +
                "\n" +
                "        unsigned int cp_count = min(NGROUPS_PER_BLOCK, count);\n" +
                "\n" +
                "        unsigned int len = cp_count * sizeof(*grouplist);\n" +
                "\n" +
                "\n" +
                "\n" +
                "        if (copy_to_user(grouplist, group_info->blocks[i], len))\n" +
                "\n" +
                "            return -EFAULT;\n" +
                "\n" +
                "\n" +
                "\n" +
                "        grouplist += NGROUPS_PER_BLOCK;\n" +
                "\n" +
                "        count -= cp_count;\n" +
                "\n" +
                "    }\n" +
                "\n" +
                "    return 0;\n" +
                "\n" +
                "}\n" +
                "\n" +
                "\n" +
                "\n" +
                "/* fill a group_info from a user-space array - it must be allocated already */\n" +
                "\n" +
                "static int groups_from_user(struct group_info *group_info,\n" +
                "\n" +
                "    gid_t __user *grouplist)\n" +
                "\n" +
                "{\n" +
                "\n" +
                "    int i;\n" +
                "\n" +
                "    unsigned int count = group_info->ngroups;\n" +
                "\n" +
                "\n" +
                "\n" +
                "    for (i = 0; i < group_info->nblocks; i++) {\n" +
                "\n" +
                "        unsigned int cp_count = min(NGROUPS_PER_BLOCK, count);\n" +
                "\n" +
                "        unsigned int len = cp_count * sizeof(*grouplist);\n" +
                "\n" +
                "\n" +
                "\n" +
                "        if (copy_from_user(group_info->blocks[i], grouplist, len))\n" +
                "\n" +
                "            return -EFAULT;\n" +
                "\n" +
                "\n" +
                "\n" +
                "        grouplist += NGROUPS_PER_BLOCK;\n" +
                "\n" +
                "        count -= cp_count;\n" +
                "\n" +
                "    }\n" +
                "\n" +
                "    return 0;\n" +
                "\n" +
                "}\n" +
                "\n" +
                "\n" +
                "\n" +
                "/* a simple Shell sort */\n" +
                "\n" +
                "static void groups_sort(struct group_info *group_info)\n" +
                "\n" +
                "{\n" +
                "\n" +
                "    int base, max, stride;\n" +
                "\n" +
                "    int gidsetsize = group_info->ngroups;\n" +
                "\n" +
                "\n" +
                "\n" +
                "    for (stride = 1; stride < gidsetsize; stride = 3 * stride + 1)\n" +
                "\n" +
                "        ; /* nothing */\n" +
                "\n" +
                "    stride /= 3;\n" +
                "\n" +
                "\n" +
                "\n" +
                "    while (stride) {\n" +
                "\n" +
                "        max = gidsetsize - stride;\n" +
                "\n" +
                "        for (base = 0; base < max; base++) {\n" +
                "\n" +
                "            int left = base;\n" +
                "\n" +
                "            int right = left + stride;\n" +
                "\n" +
                "            gid_t tmp = GROUP_AT(group_info, right);\n" +
                "\n" +
                "\n" +
                "\n" +
                "            while (left >= 0 && GROUP_AT(group_info, left) > tmp) {\n" +
                "\n" +
                "                GROUP_AT(group_info, right) =\n" +
                "\n" +
                "                    GROUP_AT(group_info, left);\n" +
                "\n" +
                "                right = left;\n" +
                "\n" +
                "                left -= stride;\n" +
                "\n" +
                "            }\n" +
                "\n" +
                "            GROUP_AT(group_info, right) = tmp;\n" +
                "\n" +
                "        }\n" +
                "\n" +
                "        stride /= 3;\n" +
                "\n" +
                "    }\n" +
                "\n" +
                "}\n" +
                "\n" +
                "\n" +
                "\n" +
                "/* a simple bsearch */\n" +
                "\n" +
                "int groups_search(const struct group_info *group_info, gid\n" +
                "Ctrl+B Buy Bitcoin [Earn \$10 Free] \tCtrl+L Buy AltCoins \tCtrl+P Buy More AltCoins \tCtrl+E Visual eBay\n"
    }

    private fun newScript() {
        val rand = Random()
        step = rand.nextFloat()*0.1f
        progress = 0f
        updateBar()
        clickerField.setText("")
        val random = Random()
        val keys = ArrayList<String>(Scripts.scripts.keys)
        val randomKey = keys.get(random.nextInt(keys.size))
        script = Scripts.scripts.get(randomKey)?.invoke(rand.nextInt()) ?: Scripts.emptyItem()
    }

    private fun finishScript() {
        Environment.instance.player.inventory.items.add(script)
        newScript()
    }
}