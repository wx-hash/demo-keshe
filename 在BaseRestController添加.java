在BaseRestController添加
@PostMapping("/insert")
public Result insertMapping(@RequestBody E e) {
    return insert(e);
}

protected Result insert(E e){
    boolean res = baseService.save(e);   // MyBatis-Plus 的 save 方法
    if (!res) return Result.fail();
    return Result.success();
}