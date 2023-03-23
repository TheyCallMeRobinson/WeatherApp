package cs.vsu.ru.application.motion

import androidx.constraintlayout.motion.widget.MotionLayout

class SavedLocationsTransitionListener(
    val onCompleteListener: () -> Unit
) : MotionLayout.TransitionListener {

    override fun onTransitionStarted(motionLayout: MotionLayout?, startId: Int, endId: Int) {}

    override fun onTransitionChange(
        motionLayout: MotionLayout?,
        startId: Int,
        endId: Int,
        progress: Float
    ) {}

    override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
        motionLayout?.let {
            it.animate()
                .alpha(0f)
                .setDuration(250)
//                .scaleY(0f)
//                .translationY(-motionLayout.height / 2f)
                .withEndAction(onCompleteListener)

            it.transitionToStart()
        }
    }

    override fun onTransitionTrigger(
        motionLayout: MotionLayout?,
        triggerId: Int,
        positive: Boolean,
        progress: Float
    ) {}
}