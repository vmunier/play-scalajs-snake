package shared.services

import shared.models.Snake
import shared.models.Position
import shared.models.Block

case class SnakeService(nbBlocksInWidth: Int, nbBlocksInHeight: Int) {
  def moveSnake(snake: Snake): Snake = {
    val movedTail = moveTailForward(snake)
    val movedHead = moveHead(snake)
    snake.copy(head = movedHead, tail = movedTail)
  }

  private def moveHead(snake: Snake): Block = {
    val headPos = snake.head.pos
    val newHeadPos = Position(
      x = (nbBlocksInWidth + headPos.x + MoveService.horizontalCoef(snake.move)) % nbBlocksInWidth,
      y = (nbBlocksInHeight + headPos.y + MoveService.verticalCoef(snake.move)) % nbBlocksInHeight)

    snake.head.copy(pos = newHeadPos)
  }

  private def moveTailForward(snake: Snake): List[Block] = {
    for {
      (newTailPos, tailBlock) <- snake.blocks.init.map(_.pos) zip snake.tail
    } yield {
      tailBlock.copy(pos = newTailPos)
    }
  }
}